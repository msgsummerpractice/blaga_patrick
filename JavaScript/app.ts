type DogResponse = {
  message: string; 
  status: string;
};

const dogImageElement = document.getElementById('dogImage') as HTMLImageElement | null;
const fetchButton = document.getElementById('fetchButton') as HTMLButtonElement | null;


async function getDogImage() : Promise<void> {
  try {
    const response = await fetch('https://dog.ceo/api/breeds/image/random'); 
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }
    const data = (await response.json()) as DogResponse;
    if (dogImageElement) {
      dogImageElement.src = data.message; 
    }
  } catch (error) {
    console.error('Failed to fetch dog image:', error);
  }
}

if (fetchButton) {
  fetchButton.addEventListener('click', (event : MouseEvent) => {
    event.preventDefault(); 
    getDogImage();
  });
}

getDogImage();

