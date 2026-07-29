async function getDogImage() {
  try {
    const response = await fetch('https://dog.ceo/api/breeds/image/random'); // over network
    const data = await response.json(); // Parse response as JSON
    document.getElementById('dogImage').src = data.message; // URL of the dog image
  } catch (error) {
    console.error('Failed to fetch dog image:', error);
  }
}

getDogImage();