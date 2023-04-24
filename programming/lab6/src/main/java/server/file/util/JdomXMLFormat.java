package server.file.util;

import org.jdom2.output.Format;

/**
 * Jdom XML format container
 */
public class JdomXMLFormat {
    /**
     * Get Jdom XML formatter with 4-space indentation
     * @return Jdom XML formatter with 4-space indentation
     */
    public static Format getPrettyXMLJdomFormatWith4SpacesIndent() {
        Format f = Format.getRawFormat();
        f.setIndent("    ");
        f.setTextMode(Format.TextMode.TRIM);
        return f;
    }
}
