#version 400 core

#vert

layout (location = 0) in vec3 position;
layout (location = 1) in vec4 color;

uniform mat4 pr_matrix;
uniform mat4 ml_matrix;

out vec4 v_Color;

void main() {
    v_Color = color;
    gl_Position = pr_matrix * ml_matrix * vec4(position, 1.0);
}

#frag

layout (location = 0) out vec4 color;

in vec4 v_Color;

void main() {
    color = v_Color;
}