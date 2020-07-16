package net.fabricmc.tiny.utils.reading;

import java.io.File;
import java.nio.ByteBuffer;
import java.util.Set;

public class TarballFileFormat {

    private final Set<FileData> files;

    public TarballFileFormat()
    {
        files = null;
    }

    public void readData(ByteBuffer data)
    {
        byte[] fileName = new byte[100];
        data.get(fileName);
        long fileMode = data.getLong();
    }

    public void readFile(File file)
    {
        byte[] data = FileUtils.read(file.getAbsolutePath());
        ByteBuffer buffer = ByteBuffer.wrap(data);
        readData(buffer);
    }

    public static final class FileData {
        private String fileName;
        private ByteBuffer data;
        private final FileMeta metaData;
        private final int position;

        public FileData(String fileName, ByteBuffer data, FileMeta metaData, int position)
        {
            this.fileName = fileName;
            this.data = data;
            this.metaData = metaData;
            this.position = position;
        }
    }

    public static final class FileMeta {
        private final long created;
        private long modified;

        public FileMeta(long created, long modified)
        {
            this.created = created;
            this.modified = modified;
        }

        public long getCreated()
        {
            return created;
        }

        public long getModified()
        {
            return modified;
        }
    }

}
