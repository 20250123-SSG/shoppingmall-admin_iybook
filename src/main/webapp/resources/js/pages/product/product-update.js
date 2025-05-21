/* 사진 미리보기 */
document.addEventListener("DOMContentLoaded", () => {
  const imageBox = document.querySelector(".image-upload-box");
  const fileInput = document.getElementById("bookImage");
  const previewImg = document.getElementById("imagePreview");

  imageBox.addEventListener("click", () => {
    fileInput.click();  // 정사각형 클릭 시 파일 선택창 열기
  });

  fileInput.addEventListener("change", (e) => {
    const file = e.target.files[0];
    if (file && file.type.startsWith("image/")) {
      const reader = new FileReader();
      reader.onload = function (event) {
        previewImg.src = event.target.result; // 미리보기 이미지 설정
      };
      reader.readAsDataURL(file);
    } else {
      previewImg.src = contextPath + `/resources/img/no-image.png`;
    }
  });
});



document.addEventListener('DOMContentLoaded', () => {
  const form = document.getElementById('registerForm');

  form.addEventListener('submit', (e) => {
    const confirmSubmit = confirm("상품을 수정하시겠습니까?");
    if (!confirmSubmit) e.preventDefault();
  });
});

