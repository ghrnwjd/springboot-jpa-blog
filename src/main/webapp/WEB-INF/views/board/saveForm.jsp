<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>

<div class="container">
      <div class="form-group">
        <label for="title">Title </label>
        <input type="text" class="form-control" placeholder="Enter Title" id="title">
      </div>

         <div class="form-group">
           <label for="comment">Content : </label>
           <textarea class="form-control summernote" rows="5" id="content"></textarea>
         </div>
    </form>

    <button id="btn-save" class="btn btn-primary">글쓰기 완료</button>
</div>
<script>
      $('.summernote').summernote({
        placeholder: '내용을 입력하세요',
        tabsize: 2,
        height: 300
      });
</script>
<script src="/js/board.js"></script>
<br/>
<%@ include file="../layout/footer.jsp"%>


