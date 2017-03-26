package xyz.dreamcoder.util;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;

public final class MimeTypeUtils {

    public static String getMimeType(String fileName) {
        return getMimeType(new File(fileName));
    }

    public static String getMimeType(File file) {

        MimetypesFileTypeMap map = new MimetypesFileTypeMap();
        map.addMimeTypes("application/msword doc DOC");
        map.addMimeTypes("application/vnd.ms-excel xls XLS");
        map.addMimeTypes("application/pdf pdf PDF");
        map.addMimeTypes("text/xml xml XML");
        map.addMimeTypes("text/html html htm HTML HTM");
        map.addMimeTypes("text/plain txt text TXT TEXT");
        map.addMimeTypes("image/gif gif GIF");
        map.addMimeTypes("image/ief ief");
        map.addMimeTypes("image/jpeg jpeg jpg jpe JPG");
        map.addMimeTypes("image/tiff tiff tif");
        map.addMimeTypes("image/png png PNG");
        map.addMimeTypes("image/x-xwindowdump xwd");
        map.addMimeTypes("application/postscript ai eps ps");
        map.addMimeTypes("application/rtf rtf");
        map.addMimeTypes("application/x-tex tex");
        map.addMimeTypes("application/x-texinfo texinfo texi");
        map.addMimeTypes("application/x-troff t tr roff");
        map.addMimeTypes("audio/basic au");
        map.addMimeTypes("audio/midi midi mid");
        map.addMimeTypes("audio/x-aifc aifc");
        map.addMimeTypes("audio/x-aiff aif aiff");
        map.addMimeTypes("audio/x-mpeg mpeg mpg");
        map.addMimeTypes("audio/x-wav wav");
        map.addMimeTypes("video/mpeg mpeg mpg mpe");
        map.addMimeTypes("video/quicktime qt mov");
        map.addMimeTypes("video/x-msvideo avi");

        return map.getContentType(file);
    }
}
