
var companiesAPI = Vue.resource('/company{/id}');

Vue.component('company-row', {
    props: ['company'],
    template: '<div><i>({{company.id}}) id </i>{{company.text}} : {{company.value}}</div>'
})

Vue.component('companies-list', {
    props:['companies'],
    template:
        '<div>' +
        '<company-row v-for="company in companies" :company="company" />' +
        '</div>',
    created: function () {
        companiesAPI.get().then(result => result.json().then(
            data =>
                data.forEach(company => this.companies.push(company))
        ))
    }
})

var app = new Vue({
    el: '#app',
    template: '<companies-list :companies="companies"/>',
    data: {
        companies: [

        ]
    }
});