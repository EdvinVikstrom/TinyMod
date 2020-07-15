package net.fabricmc.tiny.render.api.utils;

import net.fabricmc.tiny.render.api.Vertex;
import net.fabricmc.tiny.utils.exceptions.ShouldNotHappenException;

public class MeshUtils {

    public static Vertex[] quadToTris(Vertex[] quad)
    {
        if (quad.length != 4)
            throw new ShouldNotHappenException("that's not a quad! A QUAD HAS 4 SIDES NOT " + quad.length + "!!!");
        Vertex[] tri = new Vertex[6];
        tri[0] = quad[0];
        tri[1] = quad[1];
        tri[2] = quad[3];
        tri[3] = quad[1];
        tri[4] = quad[2];
        tri[5] = quad[3];
        return tri;
    }

}
