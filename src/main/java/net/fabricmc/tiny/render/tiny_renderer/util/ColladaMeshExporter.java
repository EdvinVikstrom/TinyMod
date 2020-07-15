package net.fabricmc.tiny.render.tiny_renderer.util;

import net.fabricmc.tiny.render.tiny_renderer.TinyMaterial;
import net.fabricmc.tiny.render.tiny_renderer.TinyMesh;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.util.ArrayList;
import java.util.List;

public class ColladaMeshExporter extends AbstractMeshExporter {

    public ColladaMeshExporter(List<TinyMesh> meshes)
    {
        super(meshes);
    }

    @Override
    public boolean export(String filepath)
    {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document document = builder.newDocument();
            Element colladaElement = document.createElement("COLLADA");
            colladaElement.setAttribute("xmlns", "http://www.collada.org/2005/11/COLLADASchema");
            colladaElement.setAttribute("version", "1.4.1");
            colladaElement.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");


        }catch (ParserConfigurationException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
