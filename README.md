# Screenshot Manager Android App

git remote add origin https://github.com/your-username/your-repo-name.git

## Features

- Take screenshots of your screen
- Add voice notes to screenshots
- Add text notes to screenshots
- Set reminders for screenshots
- View all screenshots in a scrollable list
- Organize screenshots with timestamps

## Requirements

- Android 7.0 (API level 24) or higher
- Permissions:
  - Storage access (for saving screenshots and voice notes)
  - Screen capture
  - Audio recording

## Setup

1. Clone the repository
2. Open the project in Android Studio
3. Build and run the app on your device

## Usage

1. Launch the app
2. Grant the necessary permissions when prompted
3. Use the floating action button to take a screenshot
4. Add notes using the text input field
5. Record voice notes using the "Record Voice Note" button
6. View all your screenshots in the main list
7. Tap on a screenshot to view its details

## Technical Details

The app uses the following Android components and libraries:

- Room Database for local storage
- MediaProjection API for screen capture
- MediaRecorder for voice recording
- RecyclerView for displaying screenshots
- Glide for image loading
- ViewModel and LiveData for data management

## License

This project is licensed under the MIT License - see the LICENSE file for details. 