#version 400 core

layout (location = 0) in vec3 position;
layout (location = 1) in vec4 color;
layout (location = 2) in vec2 texCoord;
layout (location = 3) in float texIndex;

out vec4 v_Color;

uniform float t;

void main() {

    double PI = 3.14159265359;
    float y = color.w * sin( float(t * 0.25 * PI) ) + 0.5;
    v_Color = vec4(color.xyz, y);

    mat4 ml_matrix = mat4(
    2.0, 0.0, 0.0, -1.0, // first column (not row!)
    0.0, 2.0, 0.0, -1.0, // second column
    0.0, 0.0, 1.0, 0.0, // third column
    0.0, 0.0, 0.0, 1.0  // forth column
    );

    gl_Position = vec4(position, 1.0) * ml_matrix;
}