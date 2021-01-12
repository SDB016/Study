weight = int(input())
min = 0

while True:
    if (weight % 5 == 0):
        min += weight // 5
        print(min)
        break
    weight -= 3
    min +=1
    if (weight < 0):
        print(-1)
        break 
