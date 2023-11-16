# Thymeleaf
Spring Thymeleaf

[website]: https://docs.google.com/document/d/1XpvI4zWgiOVV7Gg2PxigAjBXLoA3WHivwViryt53R8o/edit?usp=sharing
Xem chi tiết ở: [Link drive][website]


Khi sử dụng @RestController, Spring Boot giả định rằng bạn đang xây dựng một API RESTful để trả về dữ liệu chứ không phải trang web (HTML) sử dụng Thymeleaf. Vì vậy, @RestController không hỗ trợ Thymeleaf một cách tự động, nó chỉ hỗ trợ trả về dữ liệu theo định dạng JSON hoặc XML (chẳng hạn như khi sử dụng @ResponseBody).

Trong khi đó, khi bạn sử dụng @Controller, Spring Boot giả định rằng bạn đang xây dựng một ứng dụng web và muốn sử dụng Thymeleaf để render các trang HTML. Vì vậy, @Controller hỗ trợ Thymeleaf một cách tự động để hiển thị dữ liệu trên các trang web.

Tóm lại muốn dùng Thymeleaf thì phải dùng @Controller, nếu dùng @RestController sẽ trả về dữ liệu dạng xml or json.

Nếu muốn sử dụng cả hai (RESTful API và Thymeleaf), bạn có thể tạo một @Controller riêng để xử lý các yêu cầu của trang web sử dụng Thymeleaf, và sử dụng @RestController riêng để xử lý các yêu cầu của API.
