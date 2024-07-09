package studios.bluemoon.utils.syntaxx;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.shaders.ProgramManager;
import com.mojang.blaze3d.shaders.Uniform;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.resources.ResourceLocation;
import org.apache.commons.io.IOUtils;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL30;
import studios.bluemoon.utils.GlobalConstants;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

public class GlShaderUtils implements GlobalConstants {

    private final int programID;
    private final int timeUniform;
//    private final int mouseUniform;
    private final int resolutionUniform;

    private final VertexBuffer vertexBuffer;
    private final ScalableCanvas scalableCanvas;

    InputStream vertexShader = GlShaderUtils.class.getResourceAsStream("/assets/studios/bluemoon/fragment/shader.vert");
    InputStream fragmentshader = GlShaderUtils.class.getResourceAsStream("/assets/studios/bluemoon/fragment/shader.frag");



    public GlShaderUtils() throws IOException {
        int shaderProgram = GlStateManager.glCreateProgram();
        GlStateManager.glAttachShader(shaderProgram, compileShader(vertexShader, GL30.GL_VERTEX_SHADER));
        GlStateManager.glAttachShader(shaderProgram, compileShader(fragmentshader, GL30.GL_FRAGMENT_SHADER));
        GlStateManager.glLinkProgram(shaderProgram);
        if (GlStateManager.glGetProgrami(shaderProgram, GL30.GL_LINK_STATUS) == 0)
            throw new IllegalStateException("Failed to link shader");

        this.programID = shaderProgram;

        ProgramManager.glUseProgram(shaderProgram);
        this.timeUniform = Uniform.glGetUniformLocation(shaderProgram, "time");
//        this.mouseUniform = GlUniform.getUniformLocation(shaderProgram, "mouse");
        this.resolutionUniform = Uniform.glGetUniformLocation(shaderProgram, "resolution");
        ProgramManager.glUseProgram(0);

        this.vertexBuffer = new VertexBuffer(VertexBuffer.Usage.DYNAMIC);
        this.scalableCanvas = new ScalableCanvas();

        this.initQuads();

    }

    public void drawShader(int width, int height) {
        ProgramManager.glUseProgram(this.programID);
        this.scalableCanvas.resize(width, height, 3);
        this.scalableCanvas.input.bindWrite(true);
        RenderSystem.activeTexture(GL13.GL_TEXTURE1);
        this.scalableCanvas.input.bindRead();

        this.vertexBuffer.bind();
        this.vertexBuffer.draw();
        this.scalableCanvas.blit(this.vertexBuffer, 1);
    }
    public void setupUniforms(float time) {
        ProgramManager.glUseProgram(this.programID);
        GL30.glUniform2f(this.resolutionUniform, this.scalableCanvas.input.width, this.scalableCanvas.input.height);
//        GL30.glUniform2f(this.mouseUniform, mouseX, mouseX);
        GL30.glUniform1f(this.timeUniform, time);
    }

    private void initQuads() {
        BufferBuilder builder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
        builder.addVertex(-1.0F, -1.0F, 1.0F).setUv(0.0F, 0.0F).setColor(1.0F, 1.0F, 1.0F, 1.0F);
        builder.addVertex(1.0F, -1.0F, 1.0F).setUv(1.0F, 0.0F).setColor(1.0F, 1.0F, 1.0F, 1.0F);
        builder.addVertex(1.0F, 1.0F, 1.0F).setUv(1.0F, 1.0F).setColor(1.0F, 1.0F, 1.0F, 1.0F);
        builder.addVertex(-1.0F, 1.0F, 1.0F).setUv(0.0F, 1.0F).setColor(1.0F, 1.0F, 1.0F, 1.0F);
        this.vertexBuffer.bind();
        this.vertexBuffer.upload(builder.buildOrThrow());
        VertexBuffer.unbind();
    }
    private String patch(String shader) {
        if (shader.contains("gl_FragColor")) {
            shader = "out vec4 fragmentColor;\n" + shader.replace("gl_FragColor", "fragmentColor");
            System.out.println("Loaded shader uses outdated OpenGL keyword 'gl_FragColor'!");
        }

        return "#version 330\n" + shader;
    }
    private int compileShader(InputStream reader, int shaderType) throws IOException {
        int shader = GlStateManager.glCreateShader(shaderType);
        GlStateManager.glShaderSource(shader, Collections.singletonList(readStreamToString(reader)));
        GlStateManager.glCompileShader(shader);
        if (GlStateManager.glGetShaderi(shader, GL30.GL_COMPILE_STATUS) == 0)
            throw new IllegalStateException("Failed to compile shader");
        return shader;
    }


    private String readStreamToString(InputStream inputStream) throws IOException {
        return patch(IOUtils.toString(inputStream, StandardCharsets.UTF_8));
    }
}
