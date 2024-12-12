<template>
  <div v-if="editor" class="container">
    <div class="control-group">
      <div class="button-group">
        <button @click="editor.chain().focus().toggleBold().run()"
                :disabled="!editor.can().chain().focus().toggleBold().run()"
                :class="{ 'is-active': editor.isActive('bold') }">
          Bold
        </button>
        <button @click="editor.chain().focus().toggleItalic().run()"
                :disabled="!editor.can().chain().focus().toggleItalic().run()"
                :class="{ 'is-active': editor.isActive('italic') }">
          Italic
        </button>
        <button @click="editor.chain().focus().toggleStrike().run()"
                :disabled="!editor.can().chain().focus().toggleStrike().run()"
                :class="{ 'is-active': editor.isActive('strike') }">
          Strike
        </button>
        <button @click="editor.chain().focus().toggleCode().run()"
                :disabled="!editor.can().chain().focus().toggleCode().run()"
                :class="{ 'is-active': editor.isActive('code') }">
          Code
        </button>
        <button @click="editor.chain().focus().setParagraph().run()"
                :class="{ 'is-active': editor.isActive('paragraph') }">
          Paragraph
        </button>
        <button @click="editor.chain().focus().toggleHeading({ level: 1 }).run()"
                :class="{ 'is-active': editor.isActive('heading', { level: 1 }) }">
          H1
        </button>
        <button @click="editor.chain().focus().toggleHeading({ level: 2 }).run()"
                :class="{ 'is-active': editor.isActive('heading', { level: 2 }) }">
          H2
        </button>
        <button @click="editor.chain().focus().toggleHeading({ level: 3 }).run()"
                :class="{ 'is-active': editor.isActive('heading', { level: 3 }) }">
          H3
        </button>
        <button @click="editor.chain().focus().toggleHeading({ level: 4 }).run()"
                :class="{ 'is-active': editor.isActive('heading', { level: 4 }) }">
          H4
        </button>
        <button @click="editor.chain().focus().toggleHeading({ level: 5 }).run()"
                :class="{ 'is-active': editor.isActive('heading', { level: 5 }) }">
          H5
        </button>
        <button @click="editor.chain().focus().toggleHeading({ level: 6 }).run()"
                :class="{ 'is-active': editor.isActive('heading', { level: 6 }) }">
          H6
        </button>
        <button @click="editor.chain().focus().toggleBulletList().run()"
                :class="{ 'is-active': editor.isActive('bulletList') }">
          Bullet list
        </button>
        <button @click="editor.chain().focus().toggleOrderedList().run()"
                :class="{ 'is-active': editor.isActive('orderedList') }">
          Ordered list
        </button>
        <button @click="editor.chain().focus().toggleCodeBlock().run()"
                :class="{ 'is-active': editor.isActive('codeBlock') }">
          Code block
        </button>
        <button @click="editor.chain().focus().toggleBlockquote().run()"
                :class="{ 'is-active': editor.isActive('blockquote') }">
          Blockquote
        </button>
        <button @click="editor.chain().focus().setHorizontalRule().run()">
          Horizontal rule
        </button>
        <input type="file" @change="handleFileUpload">
      </div>
    </div>
    <EditorContent :editor="editor"/>
  </div>
</template>

<script setup>
import {onBeforeUnmount, onMounted, ref, watch} from 'vue';
import StarterKit from '@tiptap/starter-kit';
import {Color} from '@tiptap/extension-color'
import ListItem from '@tiptap/extension-list-item';
import TextStyle from '@tiptap/extension-text-style';
import {Editor, EditorContent} from '@tiptap/vue-3';
import Image from '@tiptap/extension-image';
import axios from "axios";

const editor = ref(null);

// Props
const props = defineProps({
  modelValue: {
    type: String,
    default: '',
  },
});

// Emits
const emit = defineEmits(['update:modelValue']);

// modelValue의 변화 감지
watch(
    () => props.modelValue,
    (value) => {
      if (!editor.value) return;

      const isSame = editor.value.getHTML() === value;

      if (!isSame) {
        editor.value.commands.setContent(value, false);
      }
    }
);

// 파일 업로드 처리
const handleFileUpload = async (event) => {
  const file = event.target.files[0];
  if (file) {
    // 파일을 서버로 업로드하는 함수 호출
    const imageUrl = await uploadImage(file);

    // 서버에서 받은 이미지 URL을 에디터에 삽입
    if (editor.value) {
      const imageNode = {
        type: 'image',
        attrs: {src: imageUrl}
      };
      editor.value.chain().focus().insertContent(imageNode).run();
    }
  }
}

// 서버로 이미지 파일을 업로드
const uploadImage = async (file) => {
  const formData = new FormData();
  formData.append('file', file);
  try {
    const response = await axios.post(`http://localhost:8253/api/v1/wiki/image/upload`, formData, {
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
  // 서버에서 반환한 이미지 url 반환
  return response.data.fileUrl;
} catch (error) {
  console.error('이미지 업로드 실패', error);
  return '';
}
}

onMounted(() => {
  editor.value = new Editor({
    extensions: [
      Color.configure({types: [TextStyle.name, ListItem.name]}),
      TextStyle.configure({types: ['listItem']}),
      StarterKit,
      Image
    ],
    content: props.modelValue,
    onUpdate: () => {
      emit('update:modelValue', editor.value.getHTML());
    },
  });
});

onBeforeUnmount(() => {
  if (editor.value) {
    editor.value.destroy();
  }
});
</script>

<style lang="scss">
.tiptap {
  :first-child {
    margin-top: 0;
  }

  ul,
  ol {
    padding: 0 1rem;
    margin: 1.25rem 1rem 1.25rem 0.4rem;

    li p {
      margin-top: 0.25em;
      margin-bottom: 0.25em;
    }
  }

  h1,
  h2,
  h3,
  h4,
  h5,
  h6 {
    line-height: 1.1;
    margin-top: 2.5rem;
    text-wrap: pretty;
  }

  h1,
  h2 {
    margin-top: 3.5rem;
    margin-bottom: 1.5rem;
  }

  h1 {
    font-size: 1.4rem;
  }

  h2 {
    font-size: 1.2rem;
  }

  h3 {
    font-size: 1.1rem;
  }

  h4,
  h5,
  h6 {
    font-size: 1rem;
  }

  code {
    background-color: var(--light-purple);
    border-radius: 0.4rem;
    color: var(--black);
    font-size: 0.85rem;
    padding: 0.25em 0.3em;
  }

  pre {
    background: var(--black);
    border-radius: 0.5rem;
    color: var(--white);
    font-family: 'JetBrainsMono', monospace;
    margin: 1.5rem 0;
    padding: 0.75rem 1rem;

    code {
      background: none;
      color: inherit;
      font-size: 0.8rem;
      padding: 0;
    }
  }

  blockquote {
    border-left: 3px solid var(--gray);
    margin: 1.5rem 0;
    padding-left: 1rem;
  }

  hr {
    border-top: 1px solid var(--gray);
    margin: 2rem 0;
  }
}
</style>