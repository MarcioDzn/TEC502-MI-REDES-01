import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App.jsx'
import { GlobalStyle } from './GlobalStyled.jsx'
import "bootstrap-icons/font/bootstrap-icons.css";
import { QueryClientProvider } from '@tanstack/react-query'
import { queryClient } from './services/queryClient.js';

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <GlobalStyle />

    <QueryClientProvider client={queryClient}>
      <App />
    </QueryClientProvider>

  </React.StrictMode>,
)
