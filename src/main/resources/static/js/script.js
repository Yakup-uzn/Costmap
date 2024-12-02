document.getElementById('login-form').addEventListener('submit', async function (event) {
		    event.preventDefault();

		    const username = document.getElementById('login-username').value;
		    const password = document.getElementById('login-password').value;

		    try {
		        const response = await fetch('http://localhost:8080/login', {
		            method: 'POST',
		            headers: {
		                'Content-Type': 'application/json'
		            },
		            body: JSON.stringify({ username, password })
		        });

		        if (response.ok) {
		            const token = await response.text();
		            console.log("Token Alındı:", token);

		            localStorage.setItem('jwtToken', token);
		            showNotification('../static/logo/başarılı.png', 'Giriş başarılı!', 'dashboard.html');
		        } else {
		            const errorData = await response.json();
		            showNotification('../static/logo/remove.png', errorData.error || 'Giriş başarısız!', null);
		        }
		    } catch (error) {
		        console.error("Hata:", error);
		        showNotification('../static/logo/remove.png', 'Sunucuya bağlanırken bir hata oluştu.', null);
		    }
		});

		
		
		
		
		
		
	    function showNotification(iconUrl, message, redirectUrl) {
	        const modal = document.getElementById('notification-modal');
	        const icon = document.getElementById('notification-icon');
	        const text = document.getElementById('notification-message');

	        // Eğer birden fazla hata varsa mesajları birleştir
	        if (typeof message === 'object') {
	            let combinedMessage = '';
	            for (const [field, errorMsg] of Object.entries(message)) {
	                combinedMessage += `${field}: ${errorMsg}\n`;
	            }
	            text.textContent = combinedMessage;
	        } else {
	            text.textContent = message;
	        }

	        icon.src = iconUrl;
	        modal.style.display = 'flex';

	        // 3 saniye sonra modalı kapat ve yönlendir
	        setTimeout(() => {
	            modal.style.display = 'none';
	            if (redirectUrl) {
	                window.location.href = redirectUrl;
	            }
	        }, 3000);
	    }
		
		

	    // Kayıt Ol Modalını Yönet
	    document.addEventListener('DOMContentLoaded', function () {
	        const openModalButton = document.getElementById('open-modal');
	        const closeModalButton = document.getElementById('close-modal');
	        const modal = document.getElementById('register-modal');

	        openModalButton.addEventListener('click', function () {
	            modal.style.display = 'flex';
	        });

	        closeModalButton.addEventListener('click', function () {
	            modal.style.display = 'none';
	        });
			

	        document.getElementById('register-form').addEventListener('submit', async function (event) {
	            event.preventDefault();

	            const data = {
	                firstName: document.getElementById('firstName').value,
	                lastName: document.getElementById('lastName').value,
	                username: document.getElementById('username').value,
	                mail: document.getElementById('email').value,
	                password: document.getElementById('password').value,
	                authorities: ["ROLE_USER"]
	            };

	            try {
	                const response = await fetch('http://localhost:8080/addNewUser', {
	                    method: 'POST',
	                    headers: { 'Content-Type': 'application/json' },
	                    body: JSON.stringify(data)
	                });

	                if (response.ok) {
	                    showNotification('../static/başarılı.png', 'Kayıt başarılı!', 'login.html');
	                } else {
	                    const errorData = await response.json();

	                    // Hataları detaylı olarak göster
	                    if (errorData instanceof Object) {
	                        showNotification('../static/logo/remove.png', errorData, null);
	                    } else {
	                        showNotification('../static/logo/remove.png', 'Hata: ' + errorData.message, null);
	                    }
	                }
	            } catch (error) {
	                showNotification('../static/logo/remove.png', 'Sunucuya bağlanırken bir hata oluştu.', null);
	            }
	        });
	    });