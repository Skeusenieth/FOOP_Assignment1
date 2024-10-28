import matplotlib
matplotlib.use('Agg')  # Use non-GUI Agg backend
import matplotlib.pyplot as plt
import json
from io import BytesIO
import base64


def plot_and_return_image(json_data):
    # Parse the JSON data
    print(f"Plotting some data!!!")
    data_series = json.loads(json_data)

    # Create the plot
    plt.figure(figsize=(8, 6))
    for series_name, data in data_series.items():
        plt.plot(data, label=series_name)

    plt.xlabel('Index')
    plt.ylabel('Value')
    plt.title('Data Series')
    plt.legend()

    # Save the plot to a buffer as PNG
    buf = BytesIO()
    plt.savefig(buf, format='png')
    buf.seek(0)

    # Encode the image to Base64
    img_base64 = base64.b64encode(buf.read()).decode('utf-8')
    buf.close()

    return img_base64

# add a main function with sample usage


# Main function for standalone testing
def main():
    # Create some sample data
    sample_data = {
        "Series 1": [1, 2, 3, 4, 5, 6],
        "Series 2": [2, 3, 5, 7, 11, 13],
        "Series 3": [1, 4, 9, 16, 25, 36]
    }

    # Convert the data to JSON
    json_data = json.dumps(sample_data)

    # Call the plotting function and get the Base64 image
    img_base64 = plot_and_return_image(json_data)

    # For testing purposes, print the Base64 string (trimmed for readability)
    print(f"Base64-encoded image: {img_base64[:100]}...")  # Print only the first 100 characters
    print(len(img_base64))


if __name__ == "__main__":
    main()