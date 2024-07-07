#version 330 core

out vec4 FragColor;

uniform vec2 resolution;
uniform vec2 rectSize;
uniform vec2 rectPos;
uniform float cornerRadius;
uniform vec4 color;

float roundedRect(vec2 pos, vec2 size, float radius) {
    vec2 npos = pos - rectPos;
    vec2 halfSize = size * 0.5;
    vec2 dist = abs(npos - halfSize) - halfSize + vec2(radius);
    return length(max(dist, 0.0)) - radius;
}

void main() {
    vec2 uv = gl_FragCoord.xy / resolution;
    float sdf = roundedRect(uv * resolution, rectSize, cornerRadius);
    float alpha = smoothstep(0.0, 1.0, -sdf);
    FragColor = vec4(color.rgb, color.a * alpha);
}
