import matplotlib
matplotlib.use('Agg')  # Use non-GUI Agg backend - essential when running from Java
import matplotlib.pyplot as plt
import json
from typing import List, Dict, Any
from io import BytesIO
import base64

# Function for plotting data with x-values, x-label, y-label, and title from JSON
def plot_and_return_image(json_data: str) -> str:
    # Parse the JSON data
    print(f"Plotting some data!!!")
    parsed_data: Dict[str, Any] = json.loads(json_data)

    # Extract x-values, axis labels, title, and data series from the parsed JSON
    x_values: List[float] = parsed_data['x_values']
    data_series: Dict[str, List[float]] = parsed_data['data_series']
    xlabel: str = parsed_data.get('x_label', 'Index')  # Default to 'Index' if not provided
    ylabel: str = parsed_data.get('y_label', 'Value')  # Default to 'Value' if not provided
    title: str = parsed_data.get('title', 'Data Series')  # Default to 'Data Series'

    # Create the plot
    plt.figure(figsize=(8, 6))

    # Plot each data series
    for series_name, data in data_series.items():
        plt.plot(x_values, data, label=series_name)

    # Set axis labels and title
    plt.xlabel(xlabel)
    plt.ylabel(ylabel)
    plt.title(title)
    plt.legend()

    # plt.show()  // must be commented out when called from Java

    # Save the plot to a buffer as PNG
    buf = BytesIO()
    plt.savefig(buf, format='png')
    buf.seek(0)

    # Encode the image to Base64
    img_base64 = base64.b64encode(buf.read()).decode('utf-8')
    buf.close()

    return img_base64

# Main function for standalone testing
def main() -> None:
    # Create some sample data including x-values, labels, and title
    sample_data = {
        "x_values": [10, 20, 30, 40, 50, 60],
        "data_series": {
            "Series 1": [1, 2, 3, 4, 5, 6],
            "Series 2": [2, 3, 5, 7, 11, 13],
            "Series 3": [1, 4, 9, 16, 25, 36]
        },
        "x_label": "Custom X-Axis",
        "y_label": "Custom Y-Axis",
        "title": "Custom Plot Title"
    }

    # Convert the data to JSON
    json_data = json.dumps(sample_data)

    # Call the plotting function with JSON data
    img_base64 = plot_and_return_image(json_data)

    # For testing purposes, print the Base64 string (trimmed for readability)
    print(f"Base64-encoded image: {img_base64[:100]}...")  # Print only the first 100 characters
    print(len(img_base64))


if __name__ == "__main__":
    main()
