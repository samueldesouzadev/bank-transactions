function formatar(mascara, documento) {
    var i = documento.value.length;
    var saida = mascara.substring(0, 1);
    var texto = mascara.substring(i);
    if (texto.substring(0, 1) != saida) {
        documento.value += texto.substring(0, 1);
    }
}

new Vue({
    el: '#app',
    vuetify: new Vuetify(),
    data() {
        return {
            desserts: [],
            restMethodSuccess: '',
            restMethodError: '',
            headers: [
                {text: 'Nome', value: 'name'},
                {text: 'Número', value: 'number'},
                {text: 'Saldo', value: 'balance'},
                {text: 'Buscar', value: 'action', sortable: false}
            ],
            valid: true,
            email: 'samueldesouza.dev@gmail.com',
            alertError: false,
            alert: false,
            busca: false,
            customer: {
                number: '',
                name: '',
                saldo: '',
                transactionList: [{
                    idTransaction: '',
                    type: '',
                    value: '',
                    balanceBefore: '',
                    balanceAfter: '',
                    data: ''
                }],
            },
            account: {
                number: '',
                type: '',
                value: '',
            },
            rules: {
                required: value => value != '' || 'Obrigatório.'
            },
        }
    },
    mounted: function () {
        this.findAll();
    },
    methods: {
        findAll() {
            var self = this;
            let url = window.location.href.replace("/register.html", "") + "/account/findAll";
            fetch(url).then((resp) => resp.json()).then(function (data) {
                self.desserts = data;
            }).catch(function (error) {
                console.log(error);
            })
        },
        save(account) {
            var self = this;
            if (this.$refs.form.validate()) {
                this.snackbar = true
                var url = window.location.href.replace("/register.html", "") + "transaction";
                console.log(url);
                fetch(url, {
                         method: 'POST',
                         body: JSON.stringify(account),
                         headers: {
                             "Content-Type": "application/json"
                         },
                }).then((resp) => resp.json()).then(function (data) {
                    console.log(data);
                    if (data.idTransaction != null) {
                        self.restMethodSuccess = "salvo";
                        self.alert = true;
                        self.findAll();
                        self.cleanCustomer(self.customer);
                    } else {
                        console.log(data.localizedMessage);
                        self.alertError = true;
                        self.restMethodError = data.localizedMessage;
                    }
                }).catch(function (error) {
                    console.log(error);
                })
            }
        },
        addAddress(index) {
            var self = this;
            self.customer.transactionList.splice(index, 0, {
                 idTransaction: '',
                 type: '',
                 value: '',
                 balanceBefore: '',
                 balanceAfter: '',
                 data: ''
            })
        },
        removeAddress(index) {
            var self = this;
            self.customer.transactionList.splice(index, 1);
        },
        showAlert() {
            const options = {title: 'Info', size: 'sm'}
            this.$dialogs.alert('Your message', options)
                .then(res => {
                    console.log(res);
                })
        },
        cleanCustomer(customer) {
            customer.id = '';
            customer.type = '';
            customer.value = '';
        },
        buscaItem(item) {
            var self = this;
            self.busca = true;
            this.snackbar = true
            var url = window.location.href.replace("/register.html", "") + "/transaction/account/" + item.number;
            console.log(url);
            fetch(url).then((resp) => resp.json()).then(function (data) {
                            self.customer = item;
                            self.customer.transactionList = data;
                        }).catch(function (error) {
                            console.log(error);
                        })

        },
    }
});

