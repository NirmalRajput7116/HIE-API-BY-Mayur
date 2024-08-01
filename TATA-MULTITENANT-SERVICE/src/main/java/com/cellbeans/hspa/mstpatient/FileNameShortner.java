package com.cellbeans.hspa.mstpatient;

    public class FileNameShortner {
        public static String generateShortenedFileName(String originalFileName) {
            // Example: Get the first 10 characters of the original file name
            int maxLength = 10; // Define your desired maximum length
            if (originalFileName.length() <= maxLength) {
                return originalFileName; // If the original name is already short enough, return it as is
            } else {
                // If the original name is too long, truncate it and add a unique identifier
                String truncatedName = originalFileName.substring(0, maxLength - 3); // Keep space for the unique identifier
                String uniqueIdentifier = String.valueOf(System.currentTimeMillis()); // Unique identifier using timestamp
                return truncatedName + "_" + uniqueIdentifier;
            }
        }

        public static void main(String[] args) {
            String originalFileName = "veryLongFileNameThatNeedsToBeShortened.jpg";
            String shortenedFileName = generateShortenedFileName(originalFileName);
            System.out.println("Shortened File Name: " + shortenedFileName);
        }
    }

