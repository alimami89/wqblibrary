<%@page contentType="text/html" pageEncoding="UTF-8"%>
<h1 class="w-100 text-center my-5">Список книг</h1>
<div class="w-100 d-flex justify-content-center">
    <div class="card border-0 p-5 m-4" style="width: 30rem;">
        <form action="createBook" method="POST">
            <div class="mb-3">
                <label for="bookName" class="form-label">Название книги</label>
                <input type="text" class="form-control"  name="bookName" id="bookName" placeholder="">
            </div>
            <div class="mb-3">
                <label for="authorsId" class="form-label">Авторы</label>
                <select name="authorsId" id="authorsId" multiple="true" class="form-select">
                    <c:forEach var="author" items="${authors}">
                        <option value="${author.id}">${author.firstName} ${author.lastName}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="mb-3">
                <label for="releaseYear" class="form-label">Год публикации</label>
                <input type="text" class="form-control  w-50"  name="releaseYear" id="releaseYear" placeholder="">
            </div>
            <div class="mb-3">
                <label for="quantity" class="form-label">Количество книг</label>
                <input type="text" class="form-control w-25"  name="quantity" id="quantity" placeholder="">
            </div>
            <input class="btn btn-primary mt-3" type="submit" value="Запомнить">
        </form>
    </div>
</div>