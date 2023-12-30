#version 330 core

layout (location = 0) in vec4 position;
layout (location = 1) in vec2 tc;

uniform mat4 pr_matrix;
uniform mat4 vw_matrix = mat4(1.0);
uniform mat4 ml_matrix;
uniform mat4 rf_matrix;

out DATA
{
    vec2 tc;
} vs_out;

void main() {
    gl_Position = pr_matrix * ml_matrix * rf_matrix * position;
    vs_out.tc = tc;
}