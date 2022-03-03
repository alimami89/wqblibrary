
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<h1 class="w-100 text-center my-5">Список книг</h1>
<div class="w-100 d-flex justify-content-center">
    <c:forEach var="book" items="${books}">
        <div class="card m-2" style="width: 10rem;">
          <div class="card-body">
            <h5 class="card-title">${book.bookName}</h5>
            <p class="card-text">
                <c:forEach var="author" items="${book.authors}">
                    ${author.firstName} ${author.lastName}.
                </c:forEach>
            </p>
            <p class="card-text">${book.releaseYear}</p>
            <p class="card-text">${book.count} шт.</p>
          </div>
        </div>
    </c:forEach>
</div>
       
