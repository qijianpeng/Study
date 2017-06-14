# Lifecycle of a Storm Topology
source from [Lifecycle of a Storm Topology](http://storm.apache.org/releases/1.1.0/Lifecycle-of-a-topology.html)

> 1. The actual topology that runs is different than the topology the user specifies. The actual topology has implicit streams and an implicit "acker" bolt added to manage the acking framework (used to guarantee data processing). The implicit topology is created via the `system-topology!` function.

实际运行的`topology`与用户自定义的`topology`是有所不同的. 为了保证数据被处理, `storm`会额外增加相应的`acker` bolt 来实现_acking framework_.

# Starting a topology
- "storm jar" command executes your class with the specified arguments. `StormSubmitter.submit...`
- `StormSubmitter.submitTopology`
  - `StormSubmitter` uploads the jar if it hasn't been uploaded before.
  - Jar uploading is done via Nimbus's Thrift interface.
  - 15 kilobytes are uploaded at a time through uploadChunk
    ```java
     String uploadLocation = client.getClient().beginFileUpload();
     client.getClient().uploadChunk(uploadLocation, ByteBuffer.wrap(toSubmit));
    //这在`StormSubmitter.submitJar`中可以找到.
    //该方法return the remote location of the submitted jar. (version 1.1.0)

    client.getClient().finishFileUpload(uploadLocation);
    ```
  - `beginFileUpload` returns a path in Nimbus's inbox.
  - Second, `StormSubmitter` calls `submitTopology`.
  - The topology config is serialized using JSON (JSON is used so that writing DSL's in any language is as easy as possible)
  - Notice that the Thrift `submitTopology` call takes in the Nimbus inbox path where the jar was uploaded
- Nimbus receives the topology submission.
- Nimbus normalizes the topology configuration. The main purpose of normalization is to ensure that every single task will have the same serialization registrations, which is critical for getting serialization working correctly.
- Nimbus sets up the static state for the topology.
  - Jars and configs are kept on local filesystem because they're too big for Zookeeper. The jar and configs are copied into the path `{nimbus local dir}/stormdist/{topology id}`
  - setup-storm-static writes task -> component mapping into ZK
  - setup-heartbeats creates a ZK "directory" in which tasks can heartbeat
- Nimbus calls mk-assignment to assign tasks to machines
  - `master-code-dir`: used by supervisors to download the correct jars/configs for the topology from Nimbus
  - `task->node+port`: Map from a task id to the worker that task should be running on. (A worker is identified by a node/port pair)
  - `node->host`: A map from node id to hostname. This is used so workers know which machines to connect to to communicate with other workers. Node ids are used to identify supervisors so that multiple supervisors can be run on one machine. One place this is done is with Mesos integration.
  - `task->start-time-secs`: Contains a map from task id to the timestamp at which Nimbus launched that task. This is used by Nimbus when monitoring topologies, as tasks are given a longer timeout to heartbeat when they're first launched (the launch timeout is configured by "nimbus.task.launch.secs" config).
- Once topologies are assigned, they're initially in a deactivated mode. start-storm writes data into Zookeeper so that the cluster knows the topology is active and can start emitting tuples from spouts.
- TODO cluster state diagram (show all nodes and what's kept everywhere).
- Supervisor runs two functions in the background:
  - `synchronize-supervisor`: This is called whenever assignments in Zookeeper change and also every 10 seconds.
    - Downloads code from Nimbus for topologies assigned to this machine for which it doesn't have the code yet.
    - Writes into local filesystem what this node is supposed to be running. It writes a map from port -> LocalAssignment. LocalAssignment contains a topology id as well as the list of task ids for that worker.
  - `sync-processes`: Reads from the LFS what synchronize-supervisor wrote and compares that to what's actually running on the machine. It then starts/stops worker processes as necessary to synchronize.
- Worker processes start up through the mk-worker function
  - Worker connects to other workers and starts a thread to monitor for changes. So if a worker gets reassigned, the worker will automatically reconnect to the other worker's new location.
  - Monitors whether a topology is active or not and stores that state in the storm-active-atom variable. This variable is used by tasks to determine whether or not to call nextTuple on the spouts.
- The worker launches the actual tasks as threads within it
  - Tasks are set up through the` mk-task` function
  - Tasks set up routing function which takes in a stream and an output tuple and returns a list of task ids to send the tuple to code (there's also a 3-arity version used for direct streams)
  - Tasks set up the spout-specific or bolt-specific code with code
