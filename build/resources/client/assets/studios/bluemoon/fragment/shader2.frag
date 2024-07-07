
precision mediump float;




uniform float time;
uniform vec2 mouse;
uniform vec2 resolution;

void main( void ) {

    vec2 p = 2.0*( gl_FragCoord.xy / resolution.xy ) -1.0;
    p.x *= resolution.x/resolution.y;
    vec3 col = vec3(0);

    vec2 op = p;
    for (int i = 0; i < 256; i++) {

        p = op;
        p.x = mod(p.x+2.0+time, 4.0)-2.0;
        p.y = -abs(p.y)+ 0.6;

        float d = abs(p.y+0.5*smoothstep(0.0,1.0,p.x-mouse.x*0.0+0.0)-0.5*smoothstep(0.0,1.0,p.x+2.0));
        col += vec3(1,1,5)*2.0/(1.0+100.0*d)/(1.0+0.10*float(i));
        //if (d < 0.005) col += vec3(1)/(1.0+0.3*float(i));
        op *= 1.07;
    }
    col *= 0.1;
    gl_FragColor = vec4(col, 1.0);
}