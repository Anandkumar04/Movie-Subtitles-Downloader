# Subtitle Downloader

Subtitle Downloader is a Java command-line tool that automatically searches for and downloads subtitles for movies using the [OpenSubtitles.org API](https://opensubtitles.org/).

## Why was it created?

Manually searching for and downloading subtitles for each movie is time-consuming and repetitive. This tool automates the process, saving users time and effort.

## How does it work?

- The user provides a movie name (or a folder containing movies).
- The tool queries the OpenSubtitles.org API for matching subtitles.
- It downloads the best-matching subtitle file and saves it in the same directory as the movie (or the current directory).
- The tool supports different languages (default is English).

## Key Features

- 🚀 **Fully automated** subtitle search and download.
- 🎬 Works for **single movies or entire folders** of movies.
- 🔑 Uses the **official OpenSubtitles API** (requires a free API key).
- ☕ Written in **Java**, easy to run on any system with Java installed.

## Technologies Used

- **Java** (main programming language)
- **Maven** (build tool)
- **Gson** (for JSON parsing)
- **OpenSubtitles.org API**

## How to Use

1. **Clone the repository and navigate to the project folder:**
    ```sh
    git clone https://github.com/yourusername/subtitle-downloader.git
    cd subtitle-downloader
    ```

2. **Build the project with Maven:**
    ```sh
    mvn clean package
    ```

3. **Run the JAR file with a movie name:**
    ```sh
    java -jar target/subtitle-downloader.jar "Your Movie Name.mkv"
    ```

    **Or for a folder:**
    ```sh
    java -jar target/subtitle-downloader.jar /path/to/movie/folder
    ```

4. **API Key:**  
    You must provide your [OpenSubtitles.org API key](https://opensubtitles.stoplight.io/docs/opensubtitles-api) as an environment variable or in the config file (see the project documentation for details).

5. **Language (optional):**  
    By default, subtitles are downloaded in English. To specify a different language, use the appropriate command-line argument (see the documentation).

## Who is it for?

- Movie enthusiasts with large collections.
- Anyone who wants a quick, automated way to get subtitles for their movies.
- Users seeking a cross-platform, open-source subtitle downloader.

## License

This project is licensed under the MIT License.

---

**Enjoy your movies with perfectly synced subtitles!**
