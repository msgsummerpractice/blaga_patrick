"use strict";
const dogImageElement = document.getElementById('dogImage');
const fetchButton = document.getElementById('fetchButton');
async function getDogImage() {
    try {
        const response = await fetch('https://dog.ceo/api/breeds/image/random');
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        const data = (await response.json());
        if (dogImageElement) {
            dogImageElement.src = data.message;
        }
    }
    catch (error) {
        console.error('Failed to fetch dog image:', error);
    }
}
if (fetchButton) {
    fetchButton.addEventListener('click', (event) => {
        event.preventDefault();
        getDogImage();
    });
}
getDogImage();
