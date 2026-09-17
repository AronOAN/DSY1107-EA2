import { useState } from 'react'
import heroImg from './assets/hero.png'
import reactLogo from './assets/react.svg'
import viteLogo from './assets/vite.svg'
import './App.css'

function App() {
  const [count, setCount] = useState(0)


  const sendLog = async (level, message) => {
    const backendUrl = 'http://localhost:8082/log';
    try {
    const response = await fetch(backendUrl, {
          method: 'POST',
      headers: {
      'Content-Type': 'application/json',
      },
      body: JSON.stringify({ level, message }),
      });
      if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
      }
      const result = await response.text();
      console.log(result);
      alert(`Log '${level}' enviado!`);
      } catch (error) {
      console.error("Error al enviar el log:", error);
      alert("Error al enviar el log. Revisa la consola.");
      }
    };

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


  return (
    <>
      <section id="center">
        <div className="App">
          <h1>Sistema de Logging RabbitMQ</h1>

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
