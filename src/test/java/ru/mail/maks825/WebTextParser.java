package ru.mail.maks825;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openqa.selenium.WebElement;
import ru.mail.maks825.pages.JobAlfaBankPage;

import java.io.*;
import java.util.List;

public class WebTextParser {
    public static void parseWebText(String outputFileName, JobAlfaBankPage jobAlfaBankPage) {

        File outputFile = new File(outputFileName);
        System.out.printf("\nИмя файла: %s\n", outputFile.getName());
        System.out.printf("Путь к файлу: %s\n", outputFile.getAbsolutePath());

        try (FileOutputStream fos = new FileOutputStream(outputFile.getName())) {
            XWPFDocument textFile = new XWPFDocument();
            XWPFParagraph paragraph = textFile.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.LEFT);
            paragraph.setFirstLineIndent(22);
            XWPFRun paragraphConfig = paragraph.createRun();
            paragraphConfig.setFontFamily("CalibriRegular, Arial");
            paragraphConfig.setFontSize(16);
            paragraphConfig.setBold(true);
            paragraphConfig.setColor("ee3124");
            paragraphConfig.setText(jobAlfaBankPage.infoTitle.getText());

            List<WebElement> infoParagraphes = jobAlfaBankPage.getInfoParagraphesText();
            for (WebElement element : infoParagraphes) {
                if (element.getTagName().equals("p")) {
                    paragraph = textFile.createParagraph();
                    paragraphConfig = paragraph.createRun();
                    paragraphConfig.setFontFamily("CalibriRegular, Arial");
                    paragraphConfig.setFontSize(14);
                    paragraphConfig.setText(element.getText());
                }
                if (element.getTagName().equals("li")) {
                    paragraph = textFile.createParagraph();
                    paragraphConfig = paragraph.createRun();
                    paragraphConfig.setFontFamily("CalibriRegular, Arial");
                    paragraphConfig.setFontSize(14);
                    paragraphConfig.addTab();
                    paragraphConfig.setText("• " + element.getText());
                }
            }
            textFile.write(fos);
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch (UnsupportedEncodingException uee) {
            uee.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
