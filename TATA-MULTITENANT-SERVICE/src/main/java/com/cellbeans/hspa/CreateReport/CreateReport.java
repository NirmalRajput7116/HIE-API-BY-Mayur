package com.cellbeans.hspa.CreateReport;

import com.cellbeans.hspa.applicationproperty.Propertyconfig;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.engine.data.JsonDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class CreateReport {

    private Propertyconfig Propertyconfig;

    public String createJasperReportPDF(String fileName, JRBeanCollectionDataSource datasource) {
        String pdfStoreUrl = Propertyconfig.getPdfStoreUrl();
        String jrxUrl = Propertyconfig.getJrxUrl();
        try {
            //InputStream employeeReportStream = getClass().getResourceAsStream("/Reports/"+fileName+".jrxml");
            //   JasperReport jasperReport = JasperCompileManager.compileReport(employeeReportStream);
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(loadJasperFile(fileName, jrxUrl));
            //JRSaver.saveObject(jasperReport, "report1.jasper");
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, datasource);
            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(pdfStoreUrl + fileName + ".pdf"));
            SimplePdfReportConfiguration reportConfig = new SimplePdfReportConfiguration();
            reportConfig.setSizePageToContent(true);
            reportConfig.setForceLineBreakPolicy(false);
            SimplePdfExporterConfiguration exportConfig = new SimplePdfExporterConfiguration();
            exportConfig.setMetadataAuthor("HSPA");
            exportConfig.setEncrypted(true);
            exportConfig.setAllowedPermissionsHint("PRINTING");
            exporter.setConfiguration(reportConfig);
            exporter.setConfiguration(exportConfig);
            exporter.exportReport();
            return "PDF/" + fileName + ".pdf";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    public String createJasperReportXLS(String fileName, JRBeanCollectionDataSource datasource) {
        String pdfStoreUrl = Propertyconfig.getPdfStoreUrl();
        String jrxUrl = Propertyconfig.getJrxUrl();
        try {
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(loadJasperFile(fileName, jrxUrl));
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, datasource);
            JRXlsxExporter exporter = new JRXlsxExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(pdfStoreUrl + fileName + ".xls"));
            SimpleXlsxReportConfiguration reportConfig = new SimpleXlsxReportConfiguration();
            reportConfig.setIgnoreCellBorder(false);
            reportConfig.setWrapText(true);
            reportConfig.setIgnoreGraphics(true);
//            reportConfig.setRemoveEmptySpaceBetweenRows(true);
//            reportConfig.setMaxRowsPerSheet(500000);
//            reportConfig.setWhitePageBackground(false);
            exporter.setConfiguration(reportConfig);
            exporter.setConfiguration(reportConfig);
            exporter.exportReport();
            return "PDF/" + fileName + ".xls";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    public File loadJasperFile(String file, String fileLocation) throws IOException {
        Path reportFile = Paths.get(fileLocation);
        reportFile = reportFile.resolve(file + ".jasper");
        return reportFile.toFile();
    }

    public String createJasperReportPDFByJson(String fileName, String datasource) {
        String pdfStoreUrl = Propertyconfig.getPdfStoreUrl();
        String jrxUrl = Propertyconfig.getJrxUrl();
        try {
            JasperReport report = (JasperReport) JRLoader.loadObject(loadJasperFile(fileName, jrxUrl));
            ByteArrayInputStream jsonDataStream = new ByteArrayInputStream(datasource.getBytes());
            JsonDataSource ds = new JsonDataSource(jsonDataStream);
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, null, ds);
            JasperExportManager.exportReportToPdfFile(jasperPrint, pdfStoreUrl + fileName + ".pdf");
            return "PDF/" + fileName + ".pdf";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    public String createJasperReportXLSByJson(String fileName, String datasource) {
        String pdfStoreUrl = Propertyconfig.getPdfStoreUrl();
        String jrxUrl = Propertyconfig.getJrxUrl();
        try {
            JasperReport report = (JasperReport) JRLoader.loadObject(loadJasperFile(fileName, jrxUrl));
            ByteArrayInputStream jsonDataStream = new ByteArrayInputStream(datasource.getBytes());
            JsonDataSource ds = new JsonDataSource(jsonDataStream);
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, null, ds);
            JRXlsExporter exporter = new JRXlsExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(pdfStoreUrl + fileName + ".xls"));
            SimpleXlsReportConfiguration configuration = new SimpleXlsReportConfiguration();
            configuration.setIgnoreCellBorder(false);
            configuration.setWrapText(true);
            configuration.setIgnoreGraphics(true);
            exporter.setConfiguration(configuration);
            exporter.exportReport();
            return "PDF/" + fileName + ".xls";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    public String generateExcel(String columnName, String fileName, JSONArray array) {
        String pdfStoreUrl = Propertyconfig.getPdfStoreUrl();
        try {
            Workbook wb = new HSSFWorkbook();
            OutputStream os = new FileOutputStream(pdfStoreUrl + fileName + ".xls");
            Sheet sheet = wb.createSheet("Excel");
            String[] columnList = columnName.split(",");
            Row row = sheet.createRow(0);
            for (int i = 0; i < columnList.length; i++) {
                Cell cell = row.createCell(i);
                sheet.setColumnWidth(i, 90 * 90);
//                sheet.autoSizeColumn(i);
                cell.setCellValue(("" + columnList[i]).toUpperCase().replace("_", " "));
            }
            for (int i = 0; i < array.length(); i++) {
                row = sheet.createRow(i + 1);
                JSONObject object = array.getJSONObject(i);
                for (int j = 0; j < columnList.length; j++) {
                    Cell cell = row.createCell(j);
                    try {
                        cell.setCellValue(("" + object.get(columnList[j])).toUpperCase().replace("_", " "));
                    } catch (Exception e) {
                        cell.setCellValue(" ");
                    }
//                    CellStyle style = wb.createCellStyle();
//                    style.setWrapText(true);
//                    cell.setCellStyle(style);
                }
            }
            wb.write(os);
            os.close();
            wb = null;
            os = null;
            return "PDF/" + fileName + ".xls";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public String createJasperReportPDFByJson1(String fileName, org.json.JSONArray datasource, String[][] data) {
        String pdfStoreUrl = Propertyconfig.getPdfStoreUrl();
        String jrxUrl = Propertyconfig.getJrxUrl();
        try {
            System.out.println("pdfStoreUrl " + pdfStoreUrl);
            System.out.println("jrxUrl " + jrxUrl);
            String[] columnNames = {"A", "B", "C", "D"};
            JasperPrint jasperPrint = null;
            List<ReportData> listItems = new ArrayList<ReportData>();
            for (int i = 0; i < datasource.length(); i++) {
                org.json.JSONObject object = datasource.getJSONObject(i);
                ReportData reportData = new ReportData("" + (i + 1), "" + object.get("bill_date"),
                        "" + object.get("patient_mr_no"), "" + object.get("patientName"), "" + object.get("user_age"),
                        "" + object.get("gender_name"), "" + object.get("TYPE1"), " " + object.get("ps_name"),
                        " " + object.get("NMMCuhid"), "" + object.get("sc_policy_no"), "" + object.get("TYPE1"),
                        " " + object.get("company_name"),
                        " " + object.get("ct_name"), "" + object.get("service_name"), " " + object.get("unit_name"),
                        " " + object.get("user_name"), "" + object.get("serviceAmount"),
                        "" + object.get("bs_discount_amount"), "" + object.get("ReferEntityName"), "" + object.get("user_name"));
                listItems.add(reportData);

            }
            System.out.println("listItems size : " + listItems.size());
            DefaultTableModel tableModel;
            tableModel = new DefaultTableModel(data, columnNames);
            HashMap<String, Object> hm = new HashMap<>();
            JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(listItems);
            hm.put("keyimages", itemsJRBean);
            JasperCompileManager.compileReportToFile(jrxUrl + "\\" + fileName + ".jrxml");
            jasperPrint = JasperFillManager.fillReport(jrxUrl + "\\" + fileName + ".jasper", hm,
                    new JRTableModelDataSource(tableModel));
            OutputStream output;
            output = new FileOutputStream(new File(pdfStoreUrl + fileName + ".pdf"));
            JasperExportManager.exportReportToPdfStream(jasperPrint, output);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "PDF/" + fileName + ".pdf";
    }

    public String createJasperReportXLSByJson1(String fileName, org.json.JSONArray datasource, String[][] data) {
        String pdfStoreUrl = Propertyconfig.getPdfStoreUrl();
        String jrxUrl = Propertyconfig.getJrxUrl();
        try {
            System.out.println("pdfStoreUrl " + pdfStoreUrl);
            System.out.println("jrxUrl " + jrxUrl);
            String[] columnNames = {"A", "B", "C", "D"};
            JasperPrint jasperPrint = null;
            List<ReportData> listItems = new ArrayList<ReportData>();
            for (int i = 0; i < datasource.length(); i++) {
                org.json.JSONObject object = datasource.getJSONObject(i);
                ReportData reportData = new ReportData("" + (i + 1), "" + object.get("bill_date"),
                        "" + object.get("patient_mr_no"), "" + object.get("patientName"), "" + object.get("user_age"),
                        "" + object.get("gender_name"), "" + object.get("TYPE1"), " " + object.get("ps_name"),
                        " " + object.get("NMMCuhid"), "" + object.get("sc_policy_no"), "" + object.get("TYPE1"),
                        " " + object.get("company_name"),
                        " " + object.get("ct_name"), "" + object.get("service_name"), " " + object.get("unit_name"),
                        " " + object.get("user_name"), "" + object.get("serviceAmount"),
                        "" + object.get("bs_discount_amount"), "" + object.get("ReferEntityName"), "" + object.get("user_namefull"));
                listItems.add(reportData);

            }
            System.out.println("listItems size : " + listItems.size());
            DefaultTableModel tableModel;
            tableModel = new DefaultTableModel(data, columnNames);
            HashMap<String, Object> hm = new HashMap<>();
            JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(listItems);
            hm.put("keyimages", itemsJRBean);
            JasperCompileManager.compileReportToFile(jrxUrl + "\\" + fileName + ".jrxml");
            jasperPrint = JasperFillManager.fillReport(jrxUrl + "\\" + fileName + ".jasper", hm,
                    new JRTableModelDataSource(tableModel));
            JRXlsExporter exporter = new JRXlsExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(pdfStoreUrl + fileName + ".xls"));
            SimpleXlsReportConfiguration configuration = new SimpleXlsReportConfiguration();
//            configuration.setOnePagePerSheet(true);
            //configuration.setDetectCellType(true);
            //configuration.setCollapseRowSpan(true);
            //configuration.setRemoveEmptySpaceBetweenRows(true);
            configuration.setIgnoreCellBorder(false);
            configuration.setWrapText(true);
            configuration.setIgnoreGraphics(true);
            exporter.setConfiguration(configuration);
            exporter.exportReport();
//            OutputStream output;
//
//            output = new FileOutputStream(new File(pdfStoreUrl  + fileName + ".pdf"));
//            JasperExportManager.exportReportToPdfStream(jasperPrint, output);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "PDF/" + fileName + ".xls";
    }
}
//package com.cellbeans.hspa.CreateReport;
//
//import net.sf.jasperreports.engine.*;
//import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
//import net.sf.jasperreports.engine.data.JsonDataSource;
//import net.sf.jasperreports.engine.export.JRPdfExporter;
//import net.sf.jasperreports.engine.export.JRXlsExporter;
//import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
//import net.sf.jasperreports.engine.util.JRLoader;
//import net.sf.jasperreports.engine.util.JRSaver;
//import net.sf.jasperreports.export.*;
//import org.json.JSONArray;
//import org.springframework.stereotype.Component;
//
//import java.io.ByteArrayInputStream;
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStream;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.HashMap;
//import java.util.Map;
//
//@Component
//public class CreateReport {
//
//    public String createJasperReportPDF(String fileName, JRBeanCollectionDataSource datasource) {
//        String pdfStoreUrl = "src/main/webapp/PDF/";
//        String jrxUrl = "/src/main/resources/Reports";
//        try {
//            //InputStream employeeReportStream = getClass().getResourceAsStream("/Reports/"+fileName+".jrxml");
//            //   JasperReport jasperReport = JasperCompileManager.compileReport(employeeReportStream);
//            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(loadJasperFile(fileName, jrxUrl));
//            //JRSaver.saveObject(jasperReport, "report1.jasper");
//            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, datasource);
//            JRPdfExporter exporter = new JRPdfExporter();
//
//            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
//            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(pdfStoreUrl + fileName + ".pdf"));
//
//            SimplePdfReportConfiguration reportConfig = new SimplePdfReportConfiguration();
//            reportConfig.setSizePageToContent(true);
//            reportConfig.setForceLineBreakPolicy(false);
//
//            SimplePdfExporterConfiguration exportConfig = new SimplePdfExporterConfiguration();
//            exportConfig.setMetadataAuthor("HSPA");
//            exportConfig.setEncrypted(true);
//            exportConfig.setAllowedPermissionsHint("PRINTING");
//
//            exporter.setConfiguration(reportConfig);
//            exporter.setConfiguration(exportConfig);
//
//            exporter.exportReport();
//
//
//            return "PDF/" + fileName + ".pdf";
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "error";
//        }
//    }
//
//    public String createJasperReportXLS(String fileName, JRBeanCollectionDataSource datasource) {
//        String pdfStoreUrl = "src/main/webapp/PDF/";
//        String jrxUrl = "./src/main/resources/Reports";
//        try {
//            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(loadJasperFile(fileName, jrxUrl));
//            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, datasource);
//            JRXlsxExporter exporter = new JRXlsxExporter();
//            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
//            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(pdfStoreUrl + fileName + ".xls"));
//            SimpleXlsxReportConfiguration reportConfig = new SimpleXlsxReportConfiguration();
//            reportConfig.setIgnoreCellBorder(false);
//            reportConfig.setWrapText(true);
//            reportConfig.setIgnoreGraphics(true);
//            exporter.setConfiguration(reportConfig);
//            exporter.setConfiguration(reportConfig);
//            exporter.exportReport();
//
//            return "PDF/" + fileName + ".xls";
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "error";
//        }
//    }
//
//
//    public File loadJasperFile(String file, String fileLocation) throws IOException {
//        Path reportFile = Paths.get(fileLocation);
//        reportFile = reportFile.resolve(file + ".jasper");
//        return reportFile.toFile();
//    }
//
//    public String createJasperReportPDFByJson(String fileName, String datasource) {
//        String pdfStoreUrl = "src/main/webapp/PDF/";
//        String jrxUrl = "./src/main/resources/Reports";
//        try {
//            JasperReport report = (JasperReport) JRLoader.loadObject(loadJasperFile(fileName, jrxUrl));
//            //Convert json string to byte array.
//            ByteArrayInputStream jsonDataStream = new ByteArrayInputStream(datasource.getBytes());
//            //Create json datasource from json stream
//            JsonDataSource ds = new JsonDataSource(jsonDataStream);
//            //Create HashMap to add report parameters
////                    Map parameters = new HashMap();
////                    //Add title parameter. Make sure the key is same name as what you named the parameters in jasper report.
////                    parameters.put("title", "Jasper PDF Example");
////                    parameters.put("name", "Name");
////                    parameters.put("value", "Value");
//            //Create Jasper Print object passing report, parameter json data source.
//            JasperPrint jasperPrint = JasperFillManager.fillReport(report, null, ds);
//            //Export and save pdf to file
//            JasperExportManager.exportReportToPdfFile(jasperPrint, pdfStoreUrl + fileName + ".pdf");
//            return "PDF/" + fileName + ".pdf";
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "error";
//        }
//    }
//
//
//    public String createJasperReportXLSByJson(String fileName, String datasource) {
//        String pdfStoreUrl = "src/main/webapp/PDF/";
//        String jrxUrl = "./src/main/resources/Reports";
//        try {
//            JasperReport report = (JasperReport) JRLoader.loadObject(loadJasperFile(fileName, jrxUrl));
//            ByteArrayInputStream jsonDataStream = new ByteArrayInputStream(datasource.getBytes());
//            JsonDataSource ds = new JsonDataSource(jsonDataStream);
//            JasperPrint jasperPrint = JasperFillManager.fillReport(report, null, ds);
//            JRXlsExporter exporter = new JRXlsExporter();
//            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
//            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(pdfStoreUrl + fileName + ".xls"));
//            SimpleXlsReportConfiguration configuration = new SimpleXlsReportConfiguration();
////            configuration.setOnePagePerSheet(true);
//            //configuration.setDetectCellType(true);
//            //configuration.setCollapseRowSpan(true);
//            //configuration.setRemoveEmptySpaceBetweenRows(true);
//            configuration.setIgnoreCellBorder(false);
//            configuration.setWrapText(true);
//            configuration.setIgnoreGraphics(true);
//            exporter.setConfiguration(configuration);
//            exporter.exportReport();
//            return "PDF/" + fileName + ".xls";
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "error";
//        }
//    }
//
//}
