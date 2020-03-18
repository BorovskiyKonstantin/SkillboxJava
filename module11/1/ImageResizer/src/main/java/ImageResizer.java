import javax.imageio.*;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.Iterator;

public class ImageResizer implements Runnable {
    private File[] files;
    private String dstFolder;
    private int newWidth;
    private long start;

    public ImageResizer(File[] files, String dstFolder, int newWidth, long start) {
        this.files = files;
        this.dstFolder = dstFolder;
        this.newWidth = newWidth;
        this.start = start;
    }


    @Override
    public void run() {
        /**
         * Выбор и запуск нужных методов для сравнения результата
         */
//        methodDefault();//Дефолтный метод
//        methodJavaxt();//через библиотеку Javaxt
        methodCreated();//Свой способ на основе Javaxt
    }

    //Первоначальный метод сжатия
    private void methodDefault() {
        try {
            for (File file : files) {
                BufferedImage image = ImageIO.read(file);
                if (image == null) {
                    System.out.println("Файл " + file.getName() + " не является изображением!");
                    continue;
                }

                int newWidth = 300;
                int newHeight = (int) Math.round(
                        image.getHeight() / (image.getWidth() / (double) newWidth)
                );
                BufferedImage newImage = new BufferedImage(
                        newWidth, newHeight, BufferedImage.TYPE_INT_RGB
                );

                int widthStep = image.getWidth() / newWidth;
                int heightStep = image.getHeight() / newHeight;

                for (int x = 0; x < newWidth; x++) {
                    for (int y = 0; y < newHeight; y++) {
                        int rgb = image.getRGB(x * widthStep, y * heightStep);
                        newImage.setRGB(x, y, rgb);
                    }
                }

                File newFile = new File(dstFolder + "/default" + file.getName());
                ImageIO.write(newImage, "jpg", newFile);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("Duration(default): " + (System.currentTimeMillis() - start));
    }

    //Метод сжатия через библиотеку Javaxt
    private void methodJavaxt() {
        for (File file : files) {
            try {
                BufferedImage myImage = ImageIO.read(file);
                if (myImage == null) {
                    continue;
                }
                //Предварительный расчет высоты изображения
                int newHeight = (int) Math.round(
                        myImage.getHeight() / (myImage.getWidth() / (double) newWidth)
                );
                javaxt.io.Image xtImage = new javaxt.io.Image(file);
                xtImage.resize(newWidth, newHeight);
                xtImage.saveAs(dstFolder + "/method2 xt - " + file.getName());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            System.out.println("Duration(javaxt): " + (System.currentTimeMillis() - start));
        }
    }

    //Метод сжатия, сделанный на основе Javaxt
    private void methodCreated() {
        try {
            for (File file : files) {
                //Получение объекта BufferedImage из файла изображения
                FileInputStream input = new FileInputStream(file);
                BufferedImage myImage = null;
                //Проверка на отсутствие файла
                if (!file.exists()) {
                    throw new IllegalArgumentException("Input file not found.");
                } else {
                    try {
                        try {
                            ImageInputStream stream = ImageIO.createImageInputStream(input);
                            Iterator iter = ImageIO.getImageReaders(stream);
                            if (!iter.hasNext()) {
                                return;
                            }

                            ImageReader reader = (ImageReader) iter.next();
                            ImageReadParam param = reader.getDefaultReadParam();
                            reader.setInput(stream, true, true);

                            try {
                                myImage = reader.read(0, param);
                            } finally {
                                reader.dispose();
                                stream.close();
                            }
                            input.close();
                        } catch (Exception var10) {
                        }
                    } catch (Exception var3) {
                    }
                    //Предварительный расчет высоты изображения
                    int newHeight = (int) Math.round(
                            myImage.getHeight() / (myImage.getWidth() / (double) newWidth)
                    );

                    //Расчет оптимальных ширины и высоты
                    //Раскрытие метода resize
                    int width = myImage.getWidth();     //ширина оригинала
                    int height = myImage.getHeight();       //высота оригинала
                    int outputWidth = newWidth;     //новая расчетная ширина
                    int outputHeight = newHeight;       //новая расчетная высота

                    double ratio = 0.0D;
                    if (width > height) {
                        ratio = (double) newWidth / (double) width;
                    } else {
                        ratio = (double) newHeight / (double) height;
                    }

                    double dw = (double) width * ratio;
                    double dh = (double) height * ratio;
                    outputWidth = (int) Math.round(dw);
                    outputHeight = (int) Math.round(dh);
                    if (outputWidth > width || outputHeight > height) {
                        outputWidth = width;
                        outputHeight = height;
                    }

                    //Фильтрация myImage перед записью
                    //Раскрытие метода myImage.getScaledInstance
                    ImageFilter filter = new AreaAveragingScaleFilter(outputWidth, outputHeight);
                    ImageProducer prod = new FilteredImageSource(myImage.getSource(), filter);
                    Image image = Toolkit.getDefaultToolkit().createImage(prod);
                    BufferedImage newImage = new BufferedImage(outputWidth, outputHeight, BufferedImage.TYPE_INT_RGB);
                    Graphics2D g2d = newImage.createGraphics();
                    g2d.drawImage(image, 0, 0, (ImageObserver) null);
                    g2d.dispose();
                    g2d = null;

                    //Запись изображения в файл
                    File newFile = new File(dstFolder + "/method3 - " + file.getName());
                    ImageIO.write(newImage, "jpg", newFile);


                    /**
                     * При сохранении методом Image.saveAs из javaxt качество картинки лучше
                     */
//                    javaxt.io.Image xtNewImage = new javaxt.io.Image(newImage);
//                    xtNewImage.saveAs(dstFolder + "/method3 saveAs - " + file.getName());

                }//Проверка на отсутствие файла
            }//for(File file : files)
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("Duration(created method): " + (System.currentTimeMillis() - start));
    }
}
