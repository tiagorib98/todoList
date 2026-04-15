// ==========================================
// SISTEMA_DE_TAREFAS.EXE - MOTOR JAVASCRIPT
// ==========================================

// Função para APAGAR
function apagarTarefa(id) {
    // O Fetch faz o pedido POST "às escondidas" ao nosso Spring Boot
    fetch('/delete/' + id, { method: 'POST' })
        .then(response => {
            if (response.ok) {
                // Se o Java disse "Tudo OK", removemos a linha do ecrã instantaneamente
                let linhaDaTarefa = document.getElementById('task-' + id);
                if (linhaDaTarefa) {
                    linhaDaTarefa.remove();
                }
            }
        });
}

// Função para CONCLUIR / DESFAZER
function completarTarefa(id, botao) {
    // Pedido AJAX ao Spring Boot
    fetch('/complete/' + id, { method: 'POST' })
        .then(response => {
            if (response.ok) {

                // NOVO: O JavaScript vai espreitar para o endereço do teu navegador
                const urlParams = new URLSearchParams(window.location.search);
                const isFiltroPendentes = urlParams.get('hide') === 'true';

                let linhaDaTarefa = document.getElementById('task-' + id);
                let textoDaTarefa = document.getElementById('text-' + id);

                // Se estivermos no filtro PENDENTES e acabámos de clicar em CONCLUIR
                if (isFiltroPendentes && botao.innerText === '[ CONCLUIR ]') {
                    // A tarefa desaparece do ecrã instantaneamente!
                    if (linhaDaTarefa) {
                        linhaDaTarefa.remove();
                    }
                }
                // Se estivermos em qualquer outro filtro (TODAS ou HOJE), faz o comportamento normal
                else {
                    if (textoDaTarefa.style.textDecoration === 'line-through') {
                        textoDaTarefa.style.textDecoration = 'none';
                        textoDaTarefa.style.color = ''; // Volta à cor normal
                        botao.innerText = '[ CONCLUIR ]';
                    } else {
                        textoDaTarefa.style.textDecoration = 'line-through';
                        textoDaTarefa.style.color = '#555555'; // Cor de texto desativado
                        botao.innerText = '[ DESFAZER ]';
                    }
                }
            }
        });
}

// Função para ABRIR o Modo de Edição
function mostrarEdicao(id) {
    // Esconde a div de visualização
    document.getElementById('view-' + id).style.display = 'none';
    // Mostra a div de edição (usamos flex para ficar tudo alinhado)
    document.getElementById('edit-' + id).style.display = 'flex';
}

// Função para FECHAR o Modo de Edição (Cancelar)
function cancelarEdicao(id) {
    // Esconde a div de edição
    document.getElementById('edit-' + id).style.display = 'none';
    // Volta a mostrar a div de visualização
    document.getElementById('view-' + id).style.display = 'flex';
}