package datamodel;

import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

import datamodel.Pricing.PricingCategory;


/**
 * Class to build {@link Order} objects as a multi-step process.
 */
public final class OrderBuilder {

    /**
     * Reference to the {@link DataFactory} singleton.
     */
    private final DataFactory dataFactory = DataFactory.getInstance();

    /**
     * Collection of {@link Customer} objects indexed by {@code id}.
     */
    private final Map<Long, Customer> customers;

    /**
     * Collection of {@link Article} objects indexed by {@code id}.
     */
    private final Map<String, Article> articles;

    /**
     * {@link Pricing.PricingCategory} used by {@link OrderBuilder} instance.
     */
    private final PricingCategory pricingCategory;


    /**
     * Inner class to hold interim states of an partially built {@link Order}
     * object. BuildState is passed during build steps.
     */
    public final class BuildState {

        /**
         * Customer looked up from {@link customers} collection.
         */
        private Optional<Customer> customer = Optional.empty();

        /**
         * Partially built order.
         */
        private Optional<Order> order = Optional.empty();

        /**
         * Private constructor to prevent outside {@link BuildState} instantiation.
         */
        private BuildState() { }


        /**
         * Build step 1: find {@link Customer} object in {@link customers} collection
         * by a specification, either by <i>id</i> or a <i>name</i> matching a first
         * or a last name.
         * @param customerSpec input to look up {@link Customer} object in {@link customers} collection
         * @return chainable self-reference
         */
        private BuildState step1_findCustomerBySpec(String customerSpec) {
            if(customerSpec==null)
                throw new IllegalArgumentException("argument customerStr: null");
            //
            // attempt to find customer by customerStr as id, last name or first name
            this.customer = customers.values().stream()
                .filter(c -> Long.toString(c.getId()).equals(customerSpec) ||
                    c.getLastName().contains(customerSpec) ||
                    c.getFirstName().contains(customerSpec)
                )
                .findAny();
            //
            return this;
        }

        /**
         * Build step 2: create {@link Order} object using {@code create()} method from
         * {@link DataFactory}.
         * @return chainable self-reference
         */
        private BuildState step2_createOrderForCustomer() {
            this.order = dataFactory.createOrder(pricingCategory, customer, null);
            return this;
        }

        /**
         * Build step 3: add ordered items to {@link Order} object by invoking the
         *  {@code addItems} lambda expression.
         * @param addItems lambda expression invoked on created order to add items
         *  through the {@link addItem} method
         * @return chainable self-reference
         */
        private BuildState step3_attachOrderedItems(Consumer<BuildState> addItems) {
            if(order.isPresent()) {
                addItems.accept(this);
            }
            return this;
        }

        /**
         * Add {@link Article} provided by a specification (<i>id</i> or <i>description</i>)
         * to an {@link Order} object.
         * @param articleSpec specification to look up {@link Article} object in {@link articles}
         *  collection by <i>id</i> or <i>description</i>
         * @param unitsOrdered units of articles ordered
         * @return chainable self-reference
         */
        public BuildState addItem(String articleSpec, long unitsOrdered) {
            if(order.isPresent()) {
                // look up article in articles by articleStr by id
                var article = Optional.ofNullable(articles.get(articleSpec));
                if(article.isEmpty()) {
                    // if not found, look up article in articles by description
                    article = articles.values().stream()
                        .filter(a -> a.getDescription().contains(articleSpec))
                        .findAny();
                }
                // if article was found, add article as item to order
                article.ifPresent(a -> order.get().addItem(a, unitsOrdered));
            }
            return this;
        }
    }

    /**
     * Not-public constructor to create {@link OrderBuilder} instance.
     * @param pricingCategory pricing category used by {@link OrderBuilder} instance
     * @param customers collection of {@link Customer} objects as owners of orders
     * @param articles collection of {@link Article} objects used in orders
     * @throws IllegalArgumentException if arguments customers or articles are null
     */
    OrderBuilder(PricingCategory pricingCategory, Map<Long, Customer> customers, Map<String, Article> articles) {
        if(pricingCategory==null)
            throw new IllegalArgumentException("argument pricingCategory: null");
        if(customers==null)
            throw new IllegalArgumentException("argument customers: null");
        if(articles==null)
            throw new IllegalArgumentException("argument articles: null");
        //
        this.pricingCategory = pricingCategory;
        this.customers = customers;
        this.articles = articles;
    }

    /**
     * Build-method for an {@link Order} object accepting a {@link Customer}
     * specification as <i>id</i> or <i>name</i>. If a {@link Customer} object
     * was found, the {@link Order} object is created. Ordered items are added
     * through the {@code addItems} {@code Consumer} interface.
     * @param customerSpec specification to look up a {@link Customer} object by <i>id</i> or <i>name</i>
     * @param addItems {@code Consumer} interface to add items, invoked only if order was created successfully
     * @return created {@link Order} object enclosed in Optional or empty Optional
     */
    public Optional<Order> buildOrder(String customerSpec, Consumer<BuildState> addItems) {
        BuildState buildState = new BuildState()
            .step1_findCustomerBySpec(customerSpec)
            .step2_createOrderForCustomer()
            .step3_attachOrderedItems(addItems);
        //
        return buildState.order;
    }
}