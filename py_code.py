def binary_search(arr, target):
    low, high = 0, len(arr) - 1
    while low <= high:
        mid = (low + high) // 2
        if arr[mid] == target:
            return mid
        elif arr[mid] < target:
            low = mid
        else:
            high = mid - 1
    return -1


def merge_sort(lst):
    if len(lst) <= 1:
        return lst
    mid = len(lst) // 2
    left = merge_sort(lst[:mid])
    right = merge_sort(lst[mid:])    
    merged = []
    i = j = 0
    while i < len(left) and j < len(right):
        if left[i] <= right[j]:
            merged.append(left[i])
            i += 1
        else:
            merged.append(right[j])
            j += 1
    while j < len(right):
        merged.append(right[j])
        j += 1
    return merged


def dfs(graph, start, visited=None):
    if visited is None:
        visited = set()
    visited.add(start)
    result = [start]
    for neighbor in graph.get(start, []):
        if neighbor not in visited:
            result.extend(dfs(graph, neighbor))
    return result


def fib(n, memo=None):
    if memo is None:
        memo = {}
    if n in memo:
        return memo[n]
    if n <= 0:
        return 0
    elif n == 1:
        return 1
    memo[n] = fib(n - 1, memo) + fib(n - 2, memo)


def two_sum(nums, target):
    num_dict = {}
    for i, num in enumerate(nums):
        num_dict[num] = i
        if target - num in num_dict:
            return [num_dict[target - num], i]
    return []


if __name__ == "__main__":
    arr = [1, 3, 5, 7, 9, 11]
    print("Binary Search:", binary_search(arr, 7))

    unsorted = [38, 27, 43, 3, 9, 82, 10]
    print("Merge Sort:", merge_sort(unsorted))

    graph = {
        'A': ['B', 'C'],
        'B': ['D', 'E'],
        'C': ['F'],
        'D': [],
        'E': ['F'],
        'F': []
    }
    print("DFS:", dfs(graph, 'A'))

    print("Fibonacci (10):", fib(10))

    nums = [2, 7, 11, 15]
    print("Two Sum:", two_sum(nums, 9))
