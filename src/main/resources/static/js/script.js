document.addEventListener("DOMContentLoaded", function () {
  configurarBusca();
  configurarFormulario();
  configurarConclusao();
});

function configurarBusca() {
  const campoBusca = document.getElementById("buscaChamados");
  const tabela = document.getElementById("tabelaChamados");

  if (!campoBusca || !tabela) {
    return;
  }

  campoBusca.addEventListener("input", function () {
    const termo = campoBusca.value.toLowerCase().trim();
    const linhas = tabela.querySelectorAll("tbody tr");

    linhas.forEach(function (linha) {
      const texto = linha.innerText.toLowerCase();

      if (texto.includes(termo)) {
        linha.style.display = "";
      } else {
        linha.style.display = "none";
      }
    });
  });
}

function configurarFormulario() {
  const formulario = document.getElementById("formChamado");

  if (!formulario) {
    return;
  }

  formulario.addEventListener("submit", function (event) {
    const titulo = document.getElementById("titulo").value.trim();
    const descricao = document.getElementById("descricao").value.trim();
    const categoria = document.getElementById("categoria").value;
    const solicitante = document.getElementById("solicitante").value.trim();
    const email = document.getElementById("email").value.trim();

    if (
      titulo === "" ||
      descricao === "" ||
      categoria === "" ||
      solicitante === "" ||
      email === ""
    ) {
      event.preventDefault();

      alert("Preencha todos os campos obrigatórios.");

      return;
    }

    if (!validarEmail(email)) {
      event.preventDefault();

      alert("Informe um e-mail válido.");
    }
  });
}

function validarEmail(email) {
  const padrao = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

  return padrao.test(email);
}

function configurarConclusao() {
  const botao = document.getElementById("btnConcluir");

  if (!botao) {
    return;
  }

  botao.addEventListener("click", function (event) {
    const confirmar = confirm("Deseja concluir este chamado?");

    if (!confirmar) {
      event.preventDefault();
    }
  });
}
