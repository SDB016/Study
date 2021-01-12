person = int(input())
time = list(map(int, input().split()))
times = 0

time.sort()

for i in range(person):
    times += time[i] * (person - i)
    
print(times)