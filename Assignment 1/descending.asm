	.data
a:
	70
	80
	40
	20
	10
	30
	50
	60
n:
	8
	.text
main:
	addi %x0, 1, %x6
	addi %x0, 0, %x8	;x8 is used in loops to iterate (i)
	addi %x0, 0, %x11	;x11 is sed in loops to iterate (j)
	load %x0, $n, %x5	;x5 is n
	beq %x5, %x6, exit
loop1:
	load %x8, $a, %x7	;x7 is ith element in a, MAX
	addi %x8, 0, %x19
	addi %x8, 0, %x11	
	beq %x8, %x5, exit
loop2:
	addi %x11, 1, %x11	;x12 is the (x11+1)th index
	load %x11, $a, %x21	;x21 is the element at index x11
	load %x19, $a, %x7
	bgt %x21, %x7, str	;element at x11 is supposed to be MAX, if not make it MAX
loopc:
	beq %x11, %x5, switch
	jmp loop2
str:
	addi %x11, 0, %x19
	jmp loopc
exit:
	end
switch:
	load %x19, $a, %x29
	load %x8, $a, %x28
	addi %x29, 0, %x20
	addi %x28, 0, %x29
	addi %x20, 0, %x28
	store %x29, $a, %19
	store %x28, $a, %x8
	addi %x8, 1, %x8
	jmp loop1
