import pandas as pd
import matplotlib.pyplot as plt

data = pd.read_csv('../../../../performance_data.csv')

plt.figure(figsize=(10, 6))
for list_type in data['ListType'].unique():
    subset = data[data['ListType'] == list_type]
    plt.plot(subset['Size'], subset['AppendTime'], label=f"{list_type} Append")

plt.yscale('log')  # Set the y-axis to logarithmic scale
plt.title("Append Performance by List Type (Logarithmic Scale)")
plt.xlabel("Number of Elements")
plt.ylabel("Time (ns, log scale)")
plt.legend()
plt.grid(True, which="both", ls="--")
plt.savefig("../../../../docs/images/AllListGraphs_log.png")
plt.show()

efficient_lists = ["EfficientIntArrayList", "EfficientIntLinkedList"]
plt.figure(figsize=(10, 6))
for list_type in efficient_lists:
    subset = data[data['ListType'] == list_type]
    plt.plot(subset['Size'], subset['AppendTime'], label=f"{list_type} Append")

plt.yscale('log')
plt.title("Append Performance by Efficient List Types (Logarithmic Scale)")
plt.xlabel("Number of Elements")
plt.ylabel("Time (ns)")
plt.legend()
plt.grid(True, which="both", ls="--")
plt.savefig("../../../../docs/images/EfficientListGraphs_log.png")
plt.show()

data['NormalizedTime'] = data.groupby('ListType')['AppendTime'].transform(lambda x: x / x.min())

plt.figure(figsize=(10, 6))
for list_type in data['ListType'].unique():
    subset = data[data['ListType'] == list_type]
    plt.plot(subset['Size'], subset['NormalizedTime'], label=f"{list_type} Append (Normalized)")

plt.title("Relative Append Performance by List Type (Normalized)")
plt.xlabel("Number of Elements")
plt.ylabel("Normalized Time (relative to min for each type)")
plt.legend()
plt.grid(True)
plt.savefig("docs/images/RelativeListGraphs.png")
plt.show()


"""
import pandas as pd
import matplotlib.pyplot as plt

data = pd.read_csv('../../../../performance_data.csv')  # Adjust the file path if needed

data['NormalizedTime'] = data.groupby('ListType')['AppendTime'].transform(lambda x: x / x.min())

plt.figure(figsize=(10, 6))
for list_type in data['ListType'].unique():
    subset = data[data['ListType'] == list_type]
    plt.plot(subset['Size'], subset['NormalizedTime'], label=f"{list_type} Append (Normalized)")

plt.title("Relative Append Performance by List Type (All Lists, Normalized)")
plt.xlabel("Number of Elements")
plt.ylabel("Normalized Time (relative to min for each type)")
plt.legend()
plt.grid(True)
plt.savefig("../../../../docs/images/AllListGraphs_Normalized.png")
plt.show()

efficient_lists = ["EfficientIntArrayList", "EfficientIntLinkedList"]
plt.figure(figsize=(10, 6))
for list_type in efficient_lists:
    subset = data[data['ListType'] == list_type]
    plt.plot(subset['Size'], subset['NormalizedTime'], label=f"{list_type} Append (Normalized)")

plt.title("Relative Append Performance by Efficient List Types (Normalized)")
plt.xlabel("Number of Elements")
plt.ylabel("Normalized Time (relative to min for each type)")
plt.legend()
plt.grid(True)
plt.savefig("../../../../docs/images/EfficientListGraphs_Normalized.png")
plt.show()
"""