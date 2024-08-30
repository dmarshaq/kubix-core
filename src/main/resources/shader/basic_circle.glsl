#version 400 core

#render_order 1

#vert

layout (location = 0) in vec3 position;
layout (location = 1) in vec4 color;
layout (location = 2) in vec2 texCoord;
layout (location = 3) in float texIndex;
layout (location = 4) in vec3 normal;

uniform mat4 pr_matrix;
uniform mat4 ml_matrix;

out vec4 v_Color;
out vec2 v_TexCoord;
out float v_TexIndex;
out vec3 v_Normal;

void main() {
    v_Color = color;
    v_TexCoord = texCoord * 2 - 1;
    v_TexIndex = texIndex;
    v_Normal = normal;
    gl_Position = pr_matrix * ml_matrix * vec4(position, 1.0);
}

#frag

layout (location = 0) out vec4 color;

in vec4 v_Color;
in vec2 v_TexCoord;
in float v_TexIndex;
in vec3 v_Normal;

uniform sampler2D u_Textures[32];

void main() {
    int index = int(v_TexIndex);
    float dist = 1 - step(0, distance(vec2(0, 0), v_TexCoord) - 1);
    if (index > -1) {
        color = vec4(v_Color.xyz, dist * v_Color.w);
    }
    else {
        color = texture(u_Textures[index], v_TexCoord);
    }
}