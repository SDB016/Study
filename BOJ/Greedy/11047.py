n, k = map(int, input().split())
coin = []
count = 0

for _ in range(n):
    coin.append(int(input()))

for i in reversed(coin):
    count += k // i
    k %= i
    if k == 0:
        break
print(count)
