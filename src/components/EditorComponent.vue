<template>
  <div v-if="editor" class="container">
    <div class="control-group">
      <div class="button-group">
        <button @click="editor.chain().focus().toggleBold().run()"
                :disabled="!editor.can().chain().focus().toggleBold().run()"
                :class="{ 'is-active': editor.isActive('bold') }">
          <i class="fas fa-bold"></i>
        </button>
        <button @click="editor.chain().focus().toggleItalic().run()"
                :disabled="!editor.can().chain().focus().toggleItalic().run()"
                :class="{ 'is-active': editor.isActive('italic') }">
          <i class="fas fa-italic"></i>
        </button>
        <button @click="editor.chain().focus().toggleStrike().run()"
                :disabled="!editor.can().chain().focus().toggleStrike().run()"
                :class="{ 'is-active': editor.isActive('strike') }">
          <i class="fas fa-strikethrough"></i>
        </button>
        <button @click="editor.chain().focus().toggleCode().run()"
                :disabled="!editor.can().chain().focus().toggleCode().run()"
                :class="{ 'is-active': editor.isActive('code') }">
          <i class="fas fa-code"></i>
        </button>
        <button @click="editor.chain().focus().setParagraph().run()"
                :class="{ 'is-active': editor.isActive('paragraph') }">
          <i class="fas fa-paragraph"></i>
        </button>
        <button @click="editor.chain().focus().toggleHeading({ level: 1 }).run()"
                :class="{ 'is-active': editor.isActive('heading', { level: 1 }) }">
          <i class="fas fa-heading"></i><sup>1</sup>
        </button>
        <button @click="editor.chain().focus().toggleHeading({ level: 2 }).run()"
                :class="{ 'is-active': editor.isActive('heading', { level: 2 }) }">
          <i class="fas fa-heading"></i><sup>2</sup>
        </button>
        <button @click="editor.chain().focus().toggleHeading({ level: 3 }).run()"
                :class="{ 'is-active': editor.isActive('heading', { level: 3 }) }">
          <i class="fas fa-heading"></i><sup>3</sup>
        </button>
        <button @click="editor.chain().focus().toggleHeading({ level: 4 }).run()"
                :class="{ 'is-active': editor.isActive('heading', { level: 4 }) }">
          <i class="fas fa-heading"></i><sup>4</sup>
        </button>
        <button @click="editor.chain().focus().toggleHeading({ level: 5 }).run()"
                :class="{ 'is-active': editor.isActive('heading', { level: 5 }) }">
          <i class="fas fa-heading"></i><sup>5</sup>
        </button>
        <button @click="editor.chain().focus().toggleHeading({ level: 6 }).run()"
                :class="{ 'is-active': editor.isActive('heading', { level: 6 }) }">
          <i class="fas fa-heading"></i><sup>6</sup>
        </button>
        <button @click="editor.chain().focus().toggleBulletList().run()"
                :class="{ 'is-active': editor.isActive('bulletList') }">
          <i class="fas fa-list-ul"></i>
        </button>
        <button @click="editor.chain().focus().toggleOrderedList().run()"
                :class="{ 'is-active': editor.isActive('orderedList') }">
          <i class="fas fa-list-ol"></i>
        </button>
        <button @click="editor.chain().focus().toggleCodeBlock().run()"
                :class="{ 'is-active': editor.isActive('codeBlock') }">
          <i class="fas fa-file-code"></i>
        </button>
        <button @click="editor.chain().focus().toggleBlockquote().run()"
                :class="{ 'is-active': editor.isActive('blockquote') }">
          <i class="fas fa-quote-right"></i>
        </button>
        <button @click="editor.chain().focus().setHorizontalRule().run()">
          <i class="fas fa-ruler-horizontal"></i>
        </button>
        <div class="file-upload">
          <label for="file-input" class="custom-upload">
            <i class="fa-regular fa-image"></i>
          </label>
          <input id="file-input" type="file" @change="handleFileUpload" class="editor-component-file"/>
        </div>
      </div>
    </div>
    <div class="editor-container">
      <EditorContent :editor="editor" class="editor-content"/>
    </div>
  </div>
</template>

<script setup>
import "@/styles/wiki/EditorComponent.css"
import {onBeforeUnmount, onMounted, ref, watch} from 'vue';
import StarterKit from '@tiptap/starter-kit';
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
blockquote {
  border-left: 4px solid var(--yellow);
  padding-left: 1rem;
  margin: 1rem 0;
}

pre {
  background-color: var(--black);
  color: var(--white);
  border-radius: 4px;
  padding: 1rem;
  overflow-x: auto;
}

code {
  background-color: var(--light-purple);
  color: var(--purple);
  padding: 0.2rem 0.4rem;
  border-radius: 4px;
}

hr {
  border: none;
  border-top: 1px solid #ddd;
  margin: 1.5rem 0;
}

sup {
  color: var(--purple);
  font-weight: bold;
}
</style>