begin_version
3
end_version
begin_metric
0
end_metric
6
begin_variable
var0
-1
2
Atom clear(a)
<none of those>
end_variable
begin_variable
var1
-1
2
Atom clear(b)
<none of those>
end_variable
begin_variable
var2
-1
2
Atom clear(c)
<none of those>
end_variable
begin_variable
var3
-1
4
Atom on-floor(a)
Atom on-top(a, a)
Atom on-top(a, b)
Atom on-top(a, c)
end_variable
begin_variable
var4
-1
4
Atom on-floor(b)
Atom on-top(b, a)
Atom on-top(b, b)
Atom on-top(b, c)
end_variable
begin_variable
var5
-1
4
Atom on-floor(c)
Atom on-top(c, a)
Atom on-top(c, b)
Atom on-top(c, c)
end_variable
6
begin_mutex_group
4
0 0
3 1
4 1
5 1
end_mutex_group
begin_mutex_group
4
1 0
3 2
4 2
5 2
end_mutex_group
begin_mutex_group
4
2 0
3 3
4 3
5 3
end_mutex_group
begin_mutex_group
4
3 0
3 1
3 2
3 3
end_mutex_group
begin_mutex_group
4
4 0
4 1
4 2
4 3
end_mutex_group
begin_mutex_group
4
5 0
5 1
5 2
5 3
end_mutex_group
begin_state
1
1
0
0
1
2
end_state
begin_goal
4
0 0
3 2
4 3
5 0
end_goal
15
begin_operator
move-to-block a a
0
2
0 0 0 1
0 3 0 1
0
end_operator
begin_operator
move-to-block a b
1
0 0
2
0 1 0 1
0 3 0 2
0
end_operator
begin_operator
move-to-block a c
1
0 0
2
0 2 0 1
0 3 0 3
0
end_operator
begin_operator
move-to-block b a
1
1 0
2
0 0 0 1
0 4 0 1
0
end_operator
begin_operator
move-to-block b b
0
2
0 1 0 1
0 4 0 2
0
end_operator
begin_operator
move-to-block b c
1
1 0
2
0 2 0 1
0 4 0 3
0
end_operator
begin_operator
move-to-block c a
1
2 0
2
0 0 0 1
0 5 0 1
0
end_operator
begin_operator
move-to-block c b
1
2 0
2
0 1 0 1
0 5 0 2
0
end_operator
begin_operator
move-to-block c c
0
2
0 2 0 1
0 5 0 3
0
end_operator
begin_operator
move-to-floor a b
1
0 0
2
0 1 -1 0
0 3 2 0
0
end_operator
begin_operator
move-to-floor a c
1
0 0
2
0 2 -1 0
0 3 3 0
0
end_operator
begin_operator
move-to-floor b a
1
1 0
2
0 0 -1 0
0 4 1 0
0
end_operator
begin_operator
move-to-floor b c
1
1 0
2
0 2 -1 0
0 4 3 0
0
end_operator
begin_operator
move-to-floor c a
1
2 0
2
0 0 -1 0
0 5 1 0
0
end_operator
begin_operator
move-to-floor c b
1
2 0
2
0 1 -1 0
0 5 2 0
0
end_operator
0
