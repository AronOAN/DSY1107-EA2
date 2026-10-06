import { useState } from 'react'
import heroImg from './assets/hero.png'
import reactLogo from './assets/react.svg'
import viteLogo from './assets/vite.svg'
import './App.css'

function App() {
  const [count, setCount] = useState(0)


  const sendEmail = async (to, subject, body) => {
    // URL de tu controlador en Spring Boot
    const backendUrl = 'http://localhost:8082/api/v1/emails';
    
    try {
      const response = await fetch(backendUrl, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          // Ya no necesitas la cabecera 'Authorization' con guest:guest aquí
        },
        // Envías el objeto JSON limpio. Spring lo mapeará automáticamente a tu EmailPayload en Java
        body: JSON.stringify({ to, subject, body }), 
      });

      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }

      // Como tu backend responde con ResponseEntity.ok("..."), leemos la respuesta como texto plano
      const result = await response.text();
      console.log("Respuesta del backend:", result);
      
      alert(`Email enviado a la cola para '${to}'!`);

    } catch (error) {
      console.error("Error al enviar el email a Spring Boot:", error);
      alert("Error al enviar el email. Revisa la consola.");
    }
  };


 /* const sendEmail = async (to, subject, body) => {
    // Tu API de Spring Boot actúa como puente seguro hacia RabbitMQ
    const backendUrl = 'http://localhost:8082/api/v1/emails'; 
    
    try {
      const response = await fetch(backendUrl, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ to, subject, body }),
      });

      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }

      const result = await response.text();
      console.log(result);
      alert(`Email para '${to}' encolado correctamente!`);
    } catch (error) {
      console.error("Error al enviar el email:", error);
      alert("Error al enviar el email. Revisa la consola.");
    }
  };*/

  /*const sendEmail = async (to, subject, body) => {
    // URL de la API de RabbitMQ para publicar mensajes directamente en un Exchange
    const backendUrl = 'http://localhost:8082/api/v1/emails';
    
    try {
      const response = await fetch(backendUrl, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          // Credenciales por defecto (guest:guest) codificadas en Base64. Cambiar en producción.
          'Authorization': 'Basic ' + btoa('guest:guest') 
        },
        body: JSON.stringify({
          properties: {
            delivery_mode: 2, // Mensaje persistente
            content_type: "application/json"
          },
          routing_key: "emailRoutingKey", // Tu clave de enrutamiento
          payload: JSON.stringify({ to, subject, body }), // El contenido real del email
          payload_encoding: "string"
        }),
      });

      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }

      const result = await response.json();
      
      if (result.routed) {
        console.log("Mensaje recibido por RabbitMQ:", result);
        alert(`Email enviado a la cola para '${to}'!`);
      } else {
        throw new Error("El mensaje llegó a RabbitMQ pero no se pudo enrutar a ninguna cola.");
      }

    } catch (error) {
      console.error("Error al publicar en RabbitMQ:", error);
      alert("Error al enviar el email. Revisa la consola.");
    }
  };*/


        const styles = {
      container: {
        display: 'flex',
        flexDirection: 'column',
        justifyContent: 'center',
        margin: '0 16px',
      },
      fixToText: {
        display: 'flex',
        flexDirection: 'row',
        justifyContent: 'space-between',
        gap: '12px',
      },
      blueButton: {
        backgroundColor: '#007bff',
        color: '#ffffff',
        border: '2px solid #0056b3',
        borderRadius: '6px',
        padding: '10px 16px',
        cursor: 'pointer',
        fontWeight: 'bold',
      },
    };

      // 1. Defines los estados al inicio de tu componente
    const [emailTo, setEmailTo] = useState('');
    const [emailSubject, setEmailSubject] = useState('');
    const [emailBody, setEmailBody] = useState('');
  return (
    <>
      <section id="center">
        <div className="App">
          <h1>Sistema de Logging RabbitMQ</h1>
          <br />
          <div style={styles.fixToText} className="button-container">
            <button 
              type="button" 
              style={styles.blueButton} 
              className="info" 
              onClick={() => sendLog('INFO', 'El usuario ha iniciado sesión.')}
            >
              Enviar Log INFO
            </button>

            <button 
              type="button" 
              style={styles.blueButton} 
              className="warning" 
              onClick={() => sendLog('WARNING', 'El uso de CPU está al 85%.')}
            >
              Enviar Log WARNING
            </button>

            <button 
              type="button" 
              style={styles.blueButton} 
              className="error" 
              onClick={() => sendLog('ERROR', 'No se pudo conectar a la base de datos.')}
            >
              Enviar Log ERROR
            </button>



          </div>
             <h1></h1>
             <br />
            <div>
          <h1>Sistema de Envio Email y encolado del Email</h1>
          <br />
            <div>
              
              <input type="email" placeholder="Para:" onChange={(e) => setEmailTo(e.target.value)} />
              <br />
              <input type="text" placeholder="Asunto:" onChange={(e) => setEmailSubject(e.target.value)} />
              <br />
              <textarea placeholder="Mensaje:" onChange={(e) => setEmailBody(e.target.value)} />
                <br />
              <button 
                type="button" 
                style={styles.blueButton} 
                onClick={() => sendEmail(emailTo, emailSubject, emailBody)}
              >
                Enviar Email Personalizado
              </button>
            </div>

            </div>
        </div>

        <button
          type="button"
          className="counter"
          onClick={() => setCount((count) => count + 1)}
        >
          Count is {count}
        </button>
      </section>
      <div className="ticks"></div>

      <section id="next-steps">
        <div id="docs">
          <svg className="icon" role="presentation" aria-hidden="true">
            <use href="/icons.svg#documentation-icon"></use>
          </svg>
          <h2>Documentation</h2>
          <p>Your questions, answered</p>
          <ul>
            <li>
              <a href="https://vite.dev/" target="_blank">
                <img className="logo" src={viteLogo} alt="" />
                Explore Vite
              </a>
            </li>
            <li>
              <a href="https://react.dev/" target="_blank">
                <img className="button-icon" src={reactLogo} alt="" />
                Learn more
              </a>
            </li>
          </ul>
        </div>
        <div id="social">
          <svg className="icon" role="presentation" aria-hidden="true">
            <use href="/icons.svg#social-icon"></use>
          </svg>
          <h2>Connect with us</h2>
          <p>Join the Vite community</p>
          <ul>
            <li>
              <a href="https://github.com/vitejs/vite" target="_blank">
                <svg
                  className="button-icon"
                  role="presentation"
                  aria-hidden="true"
                >
                  <use href="/icons.svg#github-icon"></use>
                </svg>
                GitHub
              </a>
            </li>
            <li>
              <a href="https://chat.vite.dev/" target="_blank">
                <svg
                  className="button-icon"
                  role="presentation"
                  aria-hidden="true"
                >
                  <use href="/icons.svg#discord-icon"></use>
                </svg>
                Discord
              </a>
            </li>
            <li>
              <a href="https://x.com/vite_js" target="_blank">
                <svg
                  className="button-icon"
                  role="presentation"
                  aria-hidden="true"
                >
                  <use href="/icons.svg#x-icon"></use>
                </svg>
                X.com
              </a>
            </li>
            <li>
              <a href="https://bsky.app/profile/vite.dev" target="_blank">
                <svg
                  className="button-icon"
                  role="presentation"
                  aria-hidden="true"
                >
                  <use href="/icons.svg#bluesky-icon"></use>
                </svg>
                Bluesky
              </a>
            </li>
          </ul>
        </div>
      </section>

      <div className="ticks"></div>
      <section id="spacer"></section>
    </>
  )
}

export default App
