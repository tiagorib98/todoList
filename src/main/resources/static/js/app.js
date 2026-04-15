// ==========================================
// SISTEMA_DE_TAREFAS.EXE - MOTOR JAVASCRIPT
// ==========================================

// Lemos o "crachá" de segurança que o Thymeleaf escondeu no HTML
const csrfToken = document.querySelector('meta[name="_csrf"]').content;
const csrfHeader = document.querySelector('meta[name="_csrf_header"]').content;

// Função para APAGAR
function apagarTarefa(id) {
    fetch('/delete/' + id, {
        method: 'POST',
        headers: {
            [csrfHeader]: csrfToken // Mostramos o crachá ao Guarda-costas!
        }
    })
        .then(response => {
            if (response.ok) {
                let linhaDaTarefa = document.getElementById('task-' + id);
                if (linhaDaTarefa) {
                    linhaDaTarefa.remove();
                }
            }
        });
}

// Função para CONCLUIR / DESFAZER
function completarTarefa(id, botao) {
    fetch('/complete/' + id, {
        method: 'POST',
        headers: {
            [csrfHeader]: csrfToken // Mostramos o crachá ao Guarda-costas!
        }
    })
        .then(response => {
            if (response.ok) {
                const urlParams = new URLSearchParams(window.location.search);
                const isFiltroPendentes = urlParams.get('hide') === 'true';

                let linhaDaTarefa = document.getElementById('task-' + id);
                let textoDaTarefa = document.getElementById('text-' + id);

                if (isFiltroPendentes && botao.innerText === '[ CONCLUIR ]') {
                    if (linhaDaTarefa) {
                        linhaDaTarefa.remove();
                    }
                }
                else {
                    if (textoDaTarefa.style.textDecoration === 'line-through') {
                        textoDaTarefa.style.textDecoration = 'none';
                        textoDaTarefa.style.color = '';
                        botao.innerText = '[ CONCLUIR ]';
                    } else {
                        textoDaTarefa.style.textDecoration = 'line-through';
                        textoDaTarefa.style.color = '#555555';
                        botao.innerText = '[ DESFAZER ]';
                    }
                }
            }
        });
}

// Função para ABRIR o Modo de Edição
function mostrarEdicao(id) {
    document.getElementById('view-' + id).style.display = 'none';
    document.getElementById('edit-' + id).style.display = 'flex';
}

// Função para FECHAR o Modo de Edição (Cancelar)
function cancelarEdicao(id) {
    document.getElementById('edit-' + id).style.display = 'none';
    document.getElementById('view-' + id).style.display = 'flex';
}