#sort file lines by arbitrary columns.
lines = open("srcFile", 'r').readlines()
output = open("destFile", 'w')
col = 1
for line in sorted(lines, key=lambda line: int(float(line.split()[col]))):
    output.write(line)

output.close()