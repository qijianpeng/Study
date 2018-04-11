# stash原理
## 作用
将当前未commit的数据（但是已经进行了git add）暂存到未完结的变更堆栈。

既然是Stack，stash stack中是FILO顺序。 
## 表现
流程： 
1. dev分支stash 文件
2. checkout master
3. master分支下git add新的文件
4. 查看stash stack
5. 恢复stash中的内容

dev分支操作：
```bash
qijianpeng:gittest qijianpeng$ git stash list
stash@{0}: WIP on dev: 298b1f8 init # 3. git add stash_dev_2.md; 4. git stash
stash@{1}: WIP on dev: 298b1f8 init # 1. git add stash_dev_1.md; 2. git stash
```
指的关注的是，本地目录下在stash后，是不存在被stash的文件的, 本地目录未被add的文件依旧存在。
```bash
qijianpeng:gittest qijianpeng$ ls -a 
.         ..        .git      README.md
qijianpeng:gittest qijianpeng$ git stash list
stash@{0}: WIP on dev: 298b1f8 init # 这句话的意思是在dev分支init的version上stash了文件
stash@{1}: WIP on dev: 298b1f8 init
```

在master分支上继续stash文件的表现：
```bash
qijianpeng:gittest qijianpeng$ git stash list
stash@{0}: WIP on master: 298b1f8 init
stash@{1}: WIP on dev: 298b1f8 init
stash@{2}: WIP on dev: 298b1f8 init
```
回到dev分支，恢复stash_dev_1.md文件.
```bash
qijianpeng:gittest qijianpeng$ git stash pop stash@{2}
On branch dev
Changes to be committed:
  (use "git reset HEAD <file>..." to unstage)

	new file:   stash_dev_1.md

Untracked files:
  (use "git add <file>..." to include in what will be committed)

	stash_master_2.md

Dropped stash@{2} (a9c4e76c15a09cdad48c17933691248dc3b9f328)
qijianpeng:gittest qijianpeng$ git stash list
stash@{0}: WIP on master: 298b1f8 init
stash@{1}: WIP on dev: 298b1f8 init
```
## 结论
stash内容存储进了stack，但是在stack中能够指定相应的内容恢复，所以严格的来说，是一个list，只是在存储时是按照顺序存储的，取元素默认是pop，但可以指定弹出的index。

## MISC
stash存储的历史记录在`.git/logs/refs/stash`文件中，如下：
```bash
qijianpeng:.git qijianpeng$ cat logs/refs/stash 
0000000000000000000000000000000000000000 c642f5132451e4730222ca294e9581084c3a7c5c qijianpeng <l_lzp@sina.com> 1523373034 +0800	WIP on dev: 298b1f8 init
c642f5132451e4730222ca294e9581084c3a7c5c 7cd1ae76bb498a2fb51d3b04e0ccf7aaa652bf45 qijianpeng <l_lzp@sina.com> 1523373932 +0800	WIP on master: 298b1f8 init
```

部分内容来自： https://git-scm.com/book/zh/v1/Git-工具-储藏（Stashing）

# bak目录怎么来的
git中存储的对象类型有两种：blob与tree。 blob为文件内容，对应于 inodes 或文件内容。tree 对象可以存储文件名，同时也允许存储一组文件，对应Unix中的目录。

命令`git write-tree` 会将当前缓存区域的数据放到一个新生成的目录下，使用`git read-tree --prefix=bak <shar1>`命令会为该目录命名为`bak`并读到暂存区.
```bash
qijianpeng:gittest qijianpeng$ touch write-tree.md
qijianpeng:gittest qijianpeng$ git add write-tree.md
qijianpeng:gittest qijianpeng$ git status
	new file:   write-tree.md
qijianpeng:gittest qijianpeng$ git write-tree 
0e2b20ade2d69d47248dbb8a861a264b5facbd8d
qijianpeng:gittest qijianpeng$  git cat-file -t 0e2b20ade2d69d47248dbb8a861a264b5facbd8d
tree
qijianpeng:gittest qijianpeng$ git read-tree --prefix=bak1 0e2b20ade2d69d47248dbb8a861a264b5facbd8d
qijianpeng:gittest qijianpeng$ git status
	new file:   bak1/README.md
	new file:   bak1/bak/README.md
	new file:   bak1/bak/stash_dev_1.md
	new file:   bak1/stash_dev_1.md
	new file:   bak1/write-tree.md
	new file:   write-tree.md
```

# commit—push之间的状态
commit后，reflog往前移动。

```bash
qijianpeng:gittest qijianpeng$ git reflog
211a18e HEAD@{0}: commit: git write-tree

qijianpeng:gittest qijianpeng$ git commit -m 'commit status'

qijianpeng:gittest qijianpeng$ git reflog
a8f8846 HEAD@{0}: commit: commit status
211a18e HEAD@{1}: commit: git write-tree
```
push 后没变化，只是把本地的`commit`记录推送到了remote端。

# objects 目录存储的内容，与代码文件的关系与区别
存储的objects内容都有一个索引，每次更新并提交一个改动的代码文件，就会对新的内容生成一个object（重复的内容只会生成一次，sha1值都是一样的）。那么每个版本的区别之处在于这个索引所形成的网络有变动的部分索引的更新。

svn与git在存储上的区别之处在于，svn按照文件存储，git按照元数据存储。
via: https://blog.csdn.net/qshn2sky/article/details/77622016


RegionServer数据还未落盘，down掉怎么办？