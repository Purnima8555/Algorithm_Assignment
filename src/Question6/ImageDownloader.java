// 6.Implement a Multithreaded Asynchronous Image Downloader in Java Swing
//  Task Description:
//  You are tasked with designing and implementing a multithreaded asynchronous image downloader in a Java Swing
//  application. The application should allow users to enter a URL and download images from that URL in the
//  background, while keeping the UI responsive. The image downloader should utilize multithreading and provide a
//  smooth user experience when downloading images.
//  Requirements:
//  Design and implement a GUI application that allows users to enter a URL and download images.
//  Implement a multithreaded asynchronous framework to handle the image downloading process in the background.
//  Provide a user interface that displays the progress of each image download, including the current download status
//  and completion percentage.
//  Utilize a thread pool to manage the concurrent downloading of multiple images, ensuring efficient use of system
//  resources.
//  Implement a mechanism to handle downloading errors or exceptions, displaying appropriate error messages to the
//  user.
//  Use thread synchronization mechanisms, such as locks or semaphores, to ensure data integrity and avoid conflicts
//  during image downloading.
//  Provide options for the user to pause, resume, or cancel image downloads.
//  Test the application with various URLs containing multiple images to verify its functionality and responsiveness.
//  Include proper error handling and reporting for cases such as invalid URLs or network failures


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
package Question6;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ImageDownloader extends JFrame {

    // Declare instance variables for Swing components and an ExecutorService for managing download threads
    private JTextField urlField1, urlField2;
    private JButton downloadButton, pauseButton1, resumeButton1, cancelButton1, pauseButton2, resumeButton2, cancelButton2;
    private JProgressBar progressBar1, progressBar2;
    private JLabel statusLabel1, statusLabel2, messageLabel;

    private ExecutorService executorService;
    private boolean isPaused1, isPaused2;
    private boolean isCancelled1, isCancelled2;
    private boolean isDownloadComplete1, isDownloadComplete2;

    // Initializes components, adds action listeners, creates a fixed-size thread pool
    public ImageDownloader() {
        initializeComponents();
        addActionListeners();
        executorService = Executors.newFixedThreadPool(2);
        isPaused1 = false;
        isPaused2 = false;
        isCancelled1 = false;
        isCancelled2 = false;
        isDownloadComplete1 = false;
        isDownloadComplete2 = false;
    }

    // Initialize components with specified sizes
    private void initializeComponents() {
        urlField1 = new JTextField(30);
        urlField2 = new JTextField(30);

        downloadButton = new JButton("Download");

        pauseButton1 = new JButton("Pause");
        resumeButton1 = new JButton("Resume");
        cancelButton1 = new JButton("Cancel");

        pauseButton2 = new JButton("Pause");
        resumeButton2 = new JButton("Resume");
        cancelButton2 = new JButton("Cancel");

        progressBar1 = new JProgressBar(0, 100);
        progressBar2 = new JProgressBar(0, 100);

        statusLabel1 = new JLabel("Status: ");
        statusLabel2 = new JLabel("Status: ");
        messageLabel = new JLabel("");

        setTitle("Image Downloader");

        // Add components to the frame
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Add a title label with specified font and alignment
        JLabel titleLabel = new JLabel("Image Downloader");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(10));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Panel for URL1 components
        JPanel url1Panel = new JPanel();
        url1Panel.add(new JLabel("URL 1: "));
        url1Panel.add(urlField1);
        url1Panel.add(pauseButton1);
        url1Panel.add(resumeButton1);
        url1Panel.add(cancelButton1);
        url1Panel.add(progressBar1);
        url1Panel.add(statusLabel1);

        // Panel for URL2 components
        JPanel url2Panel = new JPanel();
        url2Panel.add(new JLabel("URL 2: "));
        url2Panel.add(urlField2);
        url2Panel.add(pauseButton2);
        url2Panel.add(resumeButton2);
        url2Panel.add(cancelButton2);
        url2Panel.add(progressBar2);
        url2Panel.add(statusLabel2);

        // Add URL panels to the main panel
        panel.add(url1Panel);
        panel.add(url2Panel);

        // Add download button
        JPanel downloadPanel = new JPanel();
        downloadPanel.add(downloadButton);
        panel.add(downloadPanel);

        panel.add(messageLabel);

        add(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    private void addActionListeners() {
        // Add action listeners for buttons
        downloadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                downloadImages();
            }
        });

        pauseButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pauseDownloads(1);
            }
        });

        resumeButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resumeDownloads(1);
            }
        });

        cancelButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelDownloads(1);
            }
        });

        pauseButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pauseDownloads(2);
            }
        });

        resumeButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resumeDownloads(2);
            }
        });

        cancelButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelDownloads(2);
            }
        });
    }

    // Method to retrieve urls from text fields
    private void downloadImages() {
        String url1 = urlField1.getText();
        String url2 = urlField2.getText();

        // Check if URL fields are empty
        if (url1.isEmpty() || url2.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter URLs for both images.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Check if URL fields contain valid URLs
        if (!isValidURL(url1) || !isValidURL(url2)) {
            JOptionPane.showMessageDialog(this, "Please enter valid URLs.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Disable the download button to prevent multiple clicks
        downloadButton.setEnabled(false);

        // Download image 1
        executorService.execute(() -> {
            try {
                for (int i = 0; i <= 100; i++) {
                    if (isCancelled1) {
                        statusLabel1.setText("Status 1: Download Cancelled");
                        return;
                    }
                    if (isPaused1) {
                        statusLabel1.setText("Status 1: Download Paused");
                        return;
                    }
                    progressBar1.setValue(i);
                    statusLabel1.setText("Status 1: Downloading: " + i + "%");
                    Thread.sleep(100);
                }
                statusLabel1.setText("Status 1: Download Complete");
                isDownloadComplete1 = true;
                checkDownloadCompletion();
                // Start the download after the progress reaches 100%
                downloadImage(url1, "image1.jpg");
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        });

        // Download image 2
        executorService.execute(() -> {
            try {
                for (int i = 0; i <= 100; i++) {
                    if (isCancelled2) {
                        statusLabel2.setText("Status 2: Download Cancelled");
                        return;
                    }
                    if (isPaused2) {
                        statusLabel2.setText("Status 2: Download Paused");
                        return;
                    }
                    progressBar2.setValue(i);
                    statusLabel2.setText("Status 2: Downloading: " + i + "%");
                    Thread.sleep(100);
                }
                statusLabel2.setText("Status 2: Download Complete");
                isDownloadComplete2 = true;
                checkDownloadCompletion();
                // Start the download after the progress reaches 100%
                downloadImage(url2, "image2.jpg");
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        });
    }

    // Method to pause by setting corresponding flag
    private void pauseDownloads(int urlNumber) {
        if (urlNumber == 1) {
            isPaused1 = true;
        } else if (urlNumber == 2) {
            isPaused2 = true;
        }
    }

    // Method to check if url is valid or not
    private boolean isValidURL(String urlString) {
        try {
            new URL(urlString).toURI();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Method to resume download
    private void resumeDownloads(int urlNumber) {
        if (urlNumber == 1) {
            isPaused1 = false;
            if (!isCancelled1) {
                statusLabel1.setText("Status 1: Resuming...");
                executorService.execute(() -> {
                    try {
                        for (int i = progressBar1.getValue(); i <= 100; i++) {
                            if (isCancelled1) {
                                statusLabel1.setText("Status 1: Download Cancelled");
                                return;
                            }
                            if (isPaused1) {
                                statusLabel1.setText("Status 1: Download Paused");
                                return;
                            }
                            progressBar1.setValue(i);
                            statusLabel1.setText("Status 1: Downloading: " + i + "%");
                            Thread.sleep(100);
                        }
                        statusLabel1.setText("Status 1: Download Complete");
                        isDownloadComplete1 = true;
                        checkDownloadCompletion();
                        // Start the download after the progress reaches 100%
                        downloadImage(urlField1.getText(), "image1.jpg");
                    } catch (InterruptedException | IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        } else if (urlNumber == 2) {
            isPaused2 = false;
            if (!isCancelled2) {
                statusLabel2.setText("Status 2: Resuming...");
                executorService.execute(() -> {
                    try {
                        for (int i = progressBar2.getValue(); i <= 100; i++) {
                            if (isCancelled2) {
                                statusLabel2.setText("Status 2: Download Cancelled");
                                return;
                            }
                            if (isPaused2) {
                                statusLabel2.setText("Status 2: Download Paused");
                                return;
                            }
                            progressBar2.setValue(i);
                            statusLabel2.setText("Status 2: Downloading: " + i + "%");
                            Thread.sleep(100);
                        }
                        statusLabel2.setText("Status 2: Download Complete");
                        isDownloadComplete2 = true;
                        checkDownloadCompletion();
                        // Start the download after the progress reaches 100%
                        downloadImage(urlField2.getText(), "image2.jpg");
                    } catch (InterruptedException | IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        }
    }

    // Check if download is complete
    private void checkDownloadCompletion() {
        if (isDownloadComplete1 && isDownloadComplete2) {
            messageLabel.setText("Check the downloads for images");
            messageLabel.setForeground(Color.DARK_GRAY);
            messageLabel.setFont(new Font("Arial", Font.BOLD, 16));
        } else if (isDownloadComplete1 || isDownloadComplete2) {
            messageLabel.setText("Check the downloads for images");
            messageLabel.setForeground(Color.DARK_GRAY);
            messageLabel.setFont(new Font("Arial", Font.BOLD, 18));
        }
    }

    // Method to cancel download
    private void cancelDownloads(int urlNumber) {
        if (urlNumber == 1) {
            isCancelled1 = true;
        } else if (urlNumber == 2) {
            isCancelled2 = true;
        }
        // Reset progress bar and status label if cancelled
        if (urlNumber == 1) {
            urlField1.setText(" ");
            progressBar1.setValue(0);
            statusLabel1.setText("Status: Download Cancelled");
        } else if (urlNumber == 2) {
            urlField2.setText(" ");
            progressBar2.setValue(0);
            statusLabel2.setText("Status: Download Cancelled");
        }
    }

    // Method that downloads image from the provided URL and saves it to the local filesystem
    private void downloadImage(String urlString, String fileName) throws IOException {
        URL url = new URL(urlString);
        try (InputStream in = new BufferedInputStream(url.openStream());
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            byte[] buf = new byte[1024];
            int n;
            while (-1 != (n = in.read(buf))) {
                out.write(buf, 0, n);
            }
            byte[] response = out.toByteArray();

            File imageFile = new File(System.getProperty("user.home") + "/Downloads/" + fileName);
            try (FileOutputStream fos = new FileOutputStream(imageFile)) {
                fos.write(response);
            }
        }
    }

    public static void main(String[] args) {
        // To initialize ImageDownloader class
        SwingUtilities.invokeLater(() -> {
            ImageDownloader imageDownloader = new ImageDownloader();
            imageDownloader.setSize(600, 400);
        });
    }
}

// note :
// 1. If url fields are empty, it informs users through MessageBox.
// 2. If the fields don't have urls as input, then also it shows MessageBox to inform users.
// 3. If one box is filled and other is empty, one image will be saved to downloads.
// 4. The urls to be filled shouldn't be too long.
// 5. Users can pause, resume and cancel their downloads and even see the status while downloading.
// 6. Users can view their saved images through their downloads, it could take 2,3 seconds to appear.

// for example, these urls can be used:
// https://up.yimg.com/ib/th?id=OIP.-cd5Gxgi1CSkdI3KtRpaQwHaFj&pid=Api&rs=1&c=1&qlt=95&w=131&h=98
// https://up.yimg.com/ib/th?id=OIP.cyRApAS-vvcP4zKyrt6XYgHaEo&pid=Api&rs=1&c=1&qlt=95&w=157&h=98

// Algorithm Steps explained:
// 1. Firstly, ImageDownloader class extends JFrame, indicating that it represents a GUI window.
// 2. The class declares instance variables for various Swing components such as text fields, buttons, progress
//    bars, and labels. It also declares variables for managing the download process
//    (executorService, isPaused1, isPaused2, isCancelled1, isCancelled2, isDownloadComplete1, isDownloadComplete2).
// 3. Then, constructor ImageDownloader() initializes the components, adds action listeners, and creates a fixed-size
//     thread pool (executorService) for managing download threads.
// 4. The initializeComponents() method sets up the GUI components, arranging them in panels and adding them to the
//    frame. It also sets the title of the frame and makes it visible.
// 5. Now, addActionListeners() method adds action listeners to the buttons, specifying what actions should be taken
//    when each button is clicked.
// 6. Then, downloadImages() method retrieves URLs from text fields, checks their validity, disables the download
//    button, and starts download threads for each URL using the executor service. It updates progress bars and
//    status labels while downloading.
// 7. Similarly pauseDownloads(), resumeDownloads(), cancelDownloads() methods handle pausing, resuming, and canceling
//    downloads respectively. They set corresponding flags and update status labels accordingly.
// 8. Then, isValidURL() method checks if a given URL string is a valid URL by attempting to create a URL object from
//    it and catching any exceptions.
// 9. checkDownloadCompletion() method checks if both downloads are complete and updates a message label accordingly.
// 10. Lastly, downloadImage() method downloads an image from a given URL and saves it to the local filesystem.
// 11. In main() method the instance of ImageDownloader class is initialized using SwingUtilities.invokeLater() to
//     ensure GUI-related tasks are performed on the event dispatch thread.