#version 400 core

#vert

layout (location = 0) in vec3 position;
layout (location = 1) in vec4 color;
layout (location = 2) in vec2 texCoord;
layout (location = 3) in float texIndex;

uniform mat4 pr_matrix;
uniform mat4 ml_matrix;

out vec4 v_Color;
out vec2 v_TexCoord;
out float v_TexIndex;

void main() {
    v_Color = color;
    v_TexCoord = texCoord;
    v_TexIndex = texIndex;
    gl_Position = pr_matrix * ml_matrix * vec4(position, 1.0);
}

#frag

layout (location = 0) out vec4 color;

in vec4 v_Color;
in vec2 v_TexCoord;
in float v_TexIndex;

uniform sampler2D u_Textures[32];

void main() {

    int index = int(v_TexIndex);
    if (index == 0) {
        color = v_Color;
    }
    else {
        color = texture(u_Textures[index], v_TexCoord);
    }
}