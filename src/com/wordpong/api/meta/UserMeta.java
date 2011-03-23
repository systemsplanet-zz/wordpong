package com.wordpong.api.meta;

//@javax.annotation.Generated(value = { "slim3-gen", "@VERSION@" }, date = "2011-03-23 11:25:57")
/** */
public final class UserMeta extends org.slim3.datastore.ModelMeta<com.wordpong.api.model.User> {

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.wordpong.api.model.User, java.lang.Boolean> activated = new org.slim3.datastore.CoreAttributeMeta<com.wordpong.api.model.User, java.lang.Boolean>(this, "activated", "activated", boolean.class);

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.wordpong.api.model.User, java.util.Date> createdAt = new org.slim3.datastore.CoreAttributeMeta<com.wordpong.api.model.User, java.util.Date>(this, "createdAt", "createdAt", java.util.Date.class);

    /** */
    public final org.slim3.datastore.StringAttributeMeta<com.wordpong.api.model.User> email = new org.slim3.datastore.StringAttributeMeta<com.wordpong.api.model.User>(this, "email", "email");

    /** */
    public final org.slim3.datastore.StringUnindexedAttributeMeta<com.wordpong.api.model.User> firstName = new org.slim3.datastore.StringUnindexedAttributeMeta<com.wordpong.api.model.User>(this, "firstName", "firstName");

    /** */
    public final org.slim3.datastore.CollectionAttributeMeta<com.wordpong.api.model.User, java.util.Set<com.google.appengine.api.datastore.Key>, com.google.appengine.api.datastore.Key> friends = new org.slim3.datastore.CollectionAttributeMeta<com.wordpong.api.model.User, java.util.Set<com.google.appengine.api.datastore.Key>, com.google.appengine.api.datastore.Key>(this, "friends", "friends", java.util.Set.class);

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.wordpong.api.model.User, com.google.appengine.api.datastore.Key> key = new org.slim3.datastore.CoreAttributeMeta<com.wordpong.api.model.User, com.google.appengine.api.datastore.Key>(this, "__key__", "key", com.google.appengine.api.datastore.Key.class);

    /** */
    public final org.slim3.datastore.StringUnindexedAttributeMeta<com.wordpong.api.model.User> lastName = new org.slim3.datastore.StringUnindexedAttributeMeta<com.wordpong.api.model.User>(this, "lastName", "lastName");

    /** */
    public final org.slim3.datastore.StringUnindexedAttributeMeta<com.wordpong.api.model.User> password = new org.slim3.datastore.StringUnindexedAttributeMeta<com.wordpong.api.model.User>(this, "password", "password");

    /** */
    public final org.slim3.datastore.StringUnindexedAttributeMeta<com.wordpong.api.model.User> pictureUrl = new org.slim3.datastore.StringUnindexedAttributeMeta<com.wordpong.api.model.User>(this, "pictureUrl", "pictureUrl");

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.wordpong.api.model.User, java.util.Date> updatedAt = new org.slim3.datastore.CoreAttributeMeta<com.wordpong.api.model.User, java.util.Date>(this, "updatedAt", "updatedAt", java.util.Date.class);

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.wordpong.api.model.User, java.lang.Long> version = new org.slim3.datastore.CoreAttributeMeta<com.wordpong.api.model.User, java.lang.Long>(this, "version", "version", java.lang.Long.class);

    private static final org.slim3.datastore.CreationDate slim3_createdAtAttributeListener = new org.slim3.datastore.CreationDate();

    private static final org.slim3.datastore.ModificationDate slim3_updatedAtAttributeListener = new org.slim3.datastore.ModificationDate();

    private static final UserMeta slim3_singleton = new UserMeta();

    /**
     * @return the singleton
     */
    public static UserMeta get() {
       return slim3_singleton;
    }

    /** */
    public UserMeta() {
        super("User", com.wordpong.api.model.User.class);
    }

    @Override
    public com.wordpong.api.model.User entityToModel(com.google.appengine.api.datastore.Entity entity) {
        com.wordpong.api.model.User model = new com.wordpong.api.model.User();
        model.setActivated(booleanToPrimitiveBoolean((java.lang.Boolean) entity.getProperty("activated")));
        model.setCreatedAt((java.util.Date) entity.getProperty("createdAt"));
        model.setEmail((java.lang.String) entity.getProperty("email"));
        model.setFirstName((java.lang.String) entity.getProperty("firstName"));
        model.setFriends(new java.util.HashSet<com.google.appengine.api.datastore.Key>(toList(com.google.appengine.api.datastore.Key.class, entity.getProperty("friends"))));
        model.setKey(entity.getKey());
        model.setLastName((java.lang.String) entity.getProperty("lastName"));
        model.setPassword((java.lang.String) entity.getProperty("password"));
        model.setPictureUrl((java.lang.String) entity.getProperty("pictureUrl"));
        model.setUpdatedAt((java.util.Date) entity.getProperty("updatedAt"));
        model.setVersion((java.lang.Long) entity.getProperty("version"));
        return model;
    }

    @Override
    public com.google.appengine.api.datastore.Entity modelToEntity(java.lang.Object model) {
        com.wordpong.api.model.User m = (com.wordpong.api.model.User) model;
        com.google.appengine.api.datastore.Entity entity = null;
        if (m.getKey() != null) {
            entity = new com.google.appengine.api.datastore.Entity(m.getKey());
        } else {
            entity = new com.google.appengine.api.datastore.Entity(kind);
        }
        entity.setUnindexedProperty("activated", m.isActivated());
        entity.setProperty("createdAt", m.getCreatedAt());
        entity.setProperty("email", m.getEmail());
        entity.setUnindexedProperty("firstName", m.getFirstName());
        entity.setProperty("friends", m.getFriends());
        entity.setUnindexedProperty("lastName", m.getLastName());
        entity.setUnindexedProperty("password", m.getPassword());
        entity.setUnindexedProperty("pictureUrl", m.getPictureUrl());
        entity.setProperty("updatedAt", m.getUpdatedAt());
        entity.setProperty("version", m.getVersion());
        entity.setProperty("slim3.schemaVersion", 1);
        return entity;
    }

    @Override
    protected com.google.appengine.api.datastore.Key getKey(Object model) {
        com.wordpong.api.model.User m = (com.wordpong.api.model.User) model;
        return m.getKey();
    }

    @Override
    protected void setKey(Object model, com.google.appengine.api.datastore.Key key) {
        validateKey(key);
        com.wordpong.api.model.User m = (com.wordpong.api.model.User) model;
        m.setKey(key);
    }

    @Override
    protected long getVersion(Object model) {
        com.wordpong.api.model.User m = (com.wordpong.api.model.User) model;
        return m.getVersion() != null ? m.getVersion().longValue() : 0L;
    }

    @Override
    protected void assignKeyToModelRefIfNecessary(com.google.appengine.api.datastore.AsyncDatastoreService ds, java.lang.Object model) {
    }

    @Override
    protected void incrementVersion(Object model) {
        com.wordpong.api.model.User m = (com.wordpong.api.model.User) model;
        long version = m.getVersion() != null ? m.getVersion().longValue() : 0L;
        m.setVersion(Long.valueOf(version + 1L));
    }

    @Override
    protected void prePut(Object model) {
        com.wordpong.api.model.User m = (com.wordpong.api.model.User) model;
        m.setCreatedAt(slim3_createdAtAttributeListener.prePut(m.getCreatedAt()));
        m.setUpdatedAt(slim3_updatedAtAttributeListener.prePut(m.getUpdatedAt()));
    }

    @Override
    public String getSchemaVersionName() {
        return "slim3.schemaVersion";
    }

    @Override
    public String getClassHierarchyListName() {
        return "slim3.classHierarchyList";
    }

    @Override
    protected boolean isCipherProperty(String propertyName) {
        return false;
    }

    @Override
    protected void modelToJson(org.slim3.datastore.json.JsonWriter writer, java.lang.Object model, int maxDepth, int currentDepth) {
        com.wordpong.api.model.User m = (com.wordpong.api.model.User) model;
        writer.beginObject();
        org.slim3.datastore.json.JsonCoder encoder = null;
        writer.setNextPropertyName("activated");
        encoder = new org.slim3.datastore.json.Default();
        encoder.encode(writer, m.isActivated());
        if(m.getCreatedAt() != null){
            writer.setNextPropertyName("createdAt");
            encoder = new org.slim3.datastore.json.Default();
            encoder.encode(writer, m.getCreatedAt());
        }
        if(m.getEmail() != null){
            writer.setNextPropertyName("email");
            encoder = new org.slim3.datastore.json.Default();
            encoder.encode(writer, m.getEmail());
        }
        if(m.getEncryptionKey() != null){
            writer.setNextPropertyName("encryptionKey");
            encoder = new org.slim3.datastore.json.Default();
            encoder.encode(writer, m.getEncryptionKey());
        }
        if(m.getFirstName() != null){
            writer.setNextPropertyName("firstName");
            encoder = new org.slim3.datastore.json.Default();
            encoder.encode(writer, m.getFirstName());
        }
        if(m.getFriends() != null){
            writer.setNextPropertyName("friends");
            encoder = new org.slim3.datastore.json.Default();
            writer.beginArray();
            for(com.google.appengine.api.datastore.Key v : m.getFriends()){
                encoder.encode(writer, v);
            }
            writer.endArray();
        }
        if(m.getKey() != null){
            writer.setNextPropertyName("key");
            encoder = new org.slim3.datastore.json.Default();
            encoder.encode(writer, m.getKey());
        }
        if(m.getLastName() != null){
            writer.setNextPropertyName("lastName");
            encoder = new org.slim3.datastore.json.Default();
            encoder.encode(writer, m.getLastName());
        }
        if(m.getPassword() != null){
            writer.setNextPropertyName("password");
            encoder = new org.slim3.datastore.json.Default();
            encoder.encode(writer, m.getPassword());
        }
        if(m.getPictureUrl() != null){
            writer.setNextPropertyName("pictureUrl");
            encoder = new org.slim3.datastore.json.Default();
            encoder.encode(writer, m.getPictureUrl());
        }
        if(m.getPreferences() != null){
            writer.setNextPropertyName("preferences");
            encoder = new org.slim3.datastore.json.Default();
            encoder.encode(writer, m.getPreferences());
        }
        if(m.getRoles() != null){
            writer.setNextPropertyName("roles");
            encoder = new org.slim3.datastore.json.Default();
            // com.wordpong.api.model.Role is not supported.
        }
        if(m.getUpdatedAt() != null){
            writer.setNextPropertyName("updatedAt");
            encoder = new org.slim3.datastore.json.Default();
            encoder.encode(writer, m.getUpdatedAt());
        }
        if(m.getVersion() != null){
            writer.setNextPropertyName("version");
            encoder = new org.slim3.datastore.json.Default();
            encoder.encode(writer, m.getVersion());
        }
        writer.endObject();
    }

    @Override
    protected com.wordpong.api.model.User jsonToModel(org.slim3.datastore.json.JsonRootReader rootReader, int maxDepth, int currentDepth) {
        com.wordpong.api.model.User m = new com.wordpong.api.model.User();
        org.slim3.datastore.json.JsonReader reader = null;
        org.slim3.datastore.json.JsonCoder decoder = null;
        reader = rootReader.newObjectReader("activated");
        decoder = new org.slim3.datastore.json.Default();
        m.setActivated(decoder.decode(reader, m.isActivated()));
        reader = rootReader.newObjectReader("createdAt");
        decoder = new org.slim3.datastore.json.Default();
        m.setCreatedAt(decoder.decode(reader, m.getCreatedAt()));
        reader = rootReader.newObjectReader("email");
        decoder = new org.slim3.datastore.json.Default();
        m.setEmail(decoder.decode(reader, m.getEmail()));
        reader = rootReader.newObjectReader("encryptionKey");
        decoder = new org.slim3.datastore.json.Default();
        m.setEncryptionKey(decoder.decode(reader, m.getEncryptionKey(), javax.crypto.SecretKey.class));
        reader = rootReader.newObjectReader("firstName");
        decoder = new org.slim3.datastore.json.Default();
        m.setFirstName(decoder.decode(reader, m.getFirstName()));
        reader = rootReader.newObjectReader("friends");
        decoder = new org.slim3.datastore.json.Default();
        {
            java.util.HashSet<com.google.appengine.api.datastore.Key> elements = new java.util.HashSet<com.google.appengine.api.datastore.Key>();
            org.slim3.datastore.json.JsonArrayReader r = rootReader.newArrayReader("friends");
            if(r != null){
                reader = r;
                int n = r.length();
                for(int i = 0; i < n; i++){
                    r.setIndex(i);
                    com.google.appengine.api.datastore.Key v = decoder.decode(reader, (com.google.appengine.api.datastore.Key)null)                    ;
                    if(v != null){
                        elements.add(v);
                    }
                }
                m.setFriends(elements);
            }
        }
        reader = rootReader.newObjectReader("key");
        decoder = new org.slim3.datastore.json.Default();
        m.setKey(decoder.decode(reader, m.getKey()));
        reader = rootReader.newObjectReader("lastName");
        decoder = new org.slim3.datastore.json.Default();
        m.setLastName(decoder.decode(reader, m.getLastName()));
        reader = rootReader.newObjectReader("password");
        decoder = new org.slim3.datastore.json.Default();
        m.setPassword(decoder.decode(reader, m.getPassword()));
        reader = rootReader.newObjectReader("pictureUrl");
        decoder = new org.slim3.datastore.json.Default();
        m.setPictureUrl(decoder.decode(reader, m.getPictureUrl()));
        reader = rootReader.newObjectReader("preferences");
        decoder = new org.slim3.datastore.json.Default();
        m.setPreferences(decoder.decode(reader, m.getPreferences(), com.wordpong.api.model.Preferences.class));
        reader = rootReader.newObjectReader("roles");
        decoder = new org.slim3.datastore.json.Default();
        reader = rootReader.newObjectReader("updatedAt");
        decoder = new org.slim3.datastore.json.Default();
        m.setUpdatedAt(decoder.decode(reader, m.getUpdatedAt()));
        reader = rootReader.newObjectReader("version");
        decoder = new org.slim3.datastore.json.Default();
        m.setVersion(decoder.decode(reader, m.getVersion()));
        return m;
    }
}