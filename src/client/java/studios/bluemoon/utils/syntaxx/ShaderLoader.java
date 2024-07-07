package studios.bluemoon.utils.syntaxx;

import org.lwjgl.opengl.GL20;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ShaderLoader {

    public static int loadShader(String vertexPath, String fragmentPath) throws IOException {
        int vertexShader = createShader(vertexPath, GL20.GL_VERTEX_SHADER);
        int fragmentShader = createShader(fragmentPath, GL20.GL_FRAGMENT_SHADER);

        int shaderProgram = GL20.glCreateProgram();
        GL20.glAttachShader(shaderProgram, vertexShader);
        GL20.glAttachShader(shaderProgram, fragmentShader);
        GL20.glLinkProgram(shaderProgram);

        if (GL20.glGetProgrami(shaderProgram, GL20.GL_LINK_STATUS) == GL20.GL_FALSE) {
            throw new RuntimeException("Shader program linking failed: " + GL20.glGetProgramInfoLog(shaderProgram));
        }

        return shaderProgram;
    }

    private static int createShader(String filePath, int shaderType) throws IOException {
        int shader = GL20.glCreateShader(shaderType);
        GL20.glShaderSource(shader, readFile(filePath));
        GL20.glCompileShader(shader);

        if (GL20.glGetShaderi(shader, GL20.GL_COMPILE_STATUS) == GL20.GL_FALSE) {
            throw new RuntimeException("Shader compilation failed: " + GL20.glGetShaderInfoLog(shader));
        }

        return shader;
    }

    private static String readFile(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString();
    }
}
