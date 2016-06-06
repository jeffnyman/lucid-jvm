package com.testerstories.testing.listeners;

import com.testerstories.testing.DriverBase;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ScreenshotListener extends TestListenerAdapter {
    @Override
    public void onTestFailure(ITestResult failingTest) {
        try {
            WebDriver driver = DriverBase.getDriver();
            String screenshotDirectory = System.getProperty("screenshotDirectory");
            String screenshotAbsolutePath = screenshotDirectory
                    + File.separator + System.currentTimeMillis()
                    + "_" + failingTest.getName() + ".png";
            File screenshot = new File(screenshotAbsolutePath);

            if (createFile(screenshot)) {
                try {
                    writeScreenshotToFile(driver, screenshot);
                } catch (ClassCastException augmentDriverObject) {
                    writeScreenshotToFile(new Augmenter().augment(driver), screenshot);
                }
                System.out.println("Generating screenshot at " + screenshotAbsolutePath);
            } else {
                System.err.println("Unable to generate screenshot at " + screenshotAbsolutePath);
            }
        } catch (Exception ex) {
            System.err.println("Unable to capture screenshot.");
            ex.printStackTrace();
        }
    }

    private boolean createFile(File screenshot) throws IOException {
        boolean fileCreated = false;

        if (screenshot.exists()) {
            fileCreated = true;
        } else {
            File parentDirectory = new File(screenshot.getParent());
            if (parentDirectory.exists() || parentDirectory.mkdirs()) {
                fileCreated = screenshot.createNewFile();
            }
        }

        return fileCreated;
    }

    private void writeScreenshotToFile(WebDriver driver, File screenshot) throws IOException {
        FileOutputStream screenshotStream = new FileOutputStream(screenshot);
        screenshotStream.write(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES));
        screenshotStream.close();
    }
}
