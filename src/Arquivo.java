import java.io.*;


public class Arquivo {

    private BufferedReader in;
    private String[] buffer;
    private int nextChar;
    private int nextTokenLin, nextTokenCol;
    private int primLin, contLin;

 
    public Arquivo(String in) {
        try {
            this.in = new BufferedReader(new FileReader("Source/"+in));
            this.initBuffer();

        } catch (IOException e) {
            throw new RuntimeException(e.toString());
        }
    }

    public void close() {
        try {
            if (this.in != null) {
                this.in.close();
                this.in = null;
            }
        } catch (IOException e) {
            throw new RuntimeException(e.toString());
        }
    }

    public boolean isEndOfFile() {
        return (this.nextTokenLin < 0);
    }

    
    public boolean isEndOfLine() {
        return (this.nextTokenLin != this.primLin);
    }

  
    public String readString() {
        String next = null;

        try {
            this.checkEOF();

            String line = this.buffer[this.nextTokenLin];
            for (int i = this.primLin; i < this.contLin; i++)
                this.buffer[i] = null;
            this.buffer[0] = line;
            this.nextTokenLin = this.primLin = 0;
            this.contLin = 1;

            int i, size = line.length();
            for (i = this.nextTokenCol; i < size; i++)
                if (line.charAt(i) == '\t')
                    break;

            next = line.substring(this.nextTokenCol, i);
            this.nextChar = i;
            this.findNext();
        } catch (IOException e) {
            throw new RuntimeException(e.toString());
        }

        return next;
    }
 
    public int readInt() {
        return Integer.valueOf(this.readString()).intValue();
    }

    public double readDouble() {
        return Double.valueOf(this.readString()).doubleValue();
    }

    
    private void initBuffer() throws IOException {
        this.buffer = new String[5];
        this.nextChar = 0;
        this.nextTokenLin = 0;
        this.primLin = this.contLin = 0;

        String line = this.in.readLine();
        if (line == null) {
            this.nextTokenLin = -1;
        } else {
            this.buffer[0] = line;
            this.contLin++;
            this.findNext();
        }
    }

    private void checkEOF() throws EOFException {
        if (this.isEndOfFile())
            throw new EOFException();
    }

       private int appendLine(String str) {
        if (this.contLin == 0)
            this.primLin = 0;

        if (this.primLin + this.contLin >= this.buffer.length) {
            String[] src = this.buffer;
            if (this.contLin >= this.buffer.length)
                this.buffer = new String[2 * this.buffer.length];

            System.arraycopy(src, this.primLin, this.buffer, 0, this.contLin);
            this.nextTokenLin -= this.primLin;
            this.primLin = 0;
        }

        buffer[this.primLin + this.contLin] = str;
        this.contLin++;
        return (this.primLin + this.contLin - 1);
    }

      private void findNext() {
        try {
            String line = this.buffer[this.primLin];
            if (line != null) {
                int size = line.length();
                for (int i = this.nextChar; i < size; i++)
                    if (line.charAt(i) != '\t') {
                        this.nextTokenCol = i;
                        return;
                    }
            }

            this.nextTokenLin = this.nextTokenCol = -1;
            while ((line = this.in.readLine()) != null) {
                int size = line.length();
                for (int i = 0; i < size; i++)
                    if (line.charAt(i) != ' ') {
                        this.nextTokenCol = i;
                        this.nextTokenLin = this.appendLine(line);
                        return;
                    }
                this.appendLine(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e.toString());
        }
    }

}
