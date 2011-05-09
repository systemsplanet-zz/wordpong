package com.wordpong.api.meta;

//@javax.annotation.Generated(value = { "slim3-gen", "@VERSION@" }, date = "2011-05-08 18:26:50")
/** */
public final class AnswerMeta extends org.slim3.datastore.ModelMeta<com.wordpong.api.model.Answer> {

    /** */
    public final org.slim3.datastore.StringCollectionAttributeMeta<com.wordpong.api.model.Answer, java.util.List<java.lang.String>> answers = new org.slim3.datastore.StringCollectionAttributeMeta<com.wordpong.api.model.Answer, java.util.List<java.lang.String>>(this, "answers", "answers", java.util.List.class);

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.wordpong.api.model.Answer, com.google.appengine.api.datastore.Key> key = new org.slim3.datastore.CoreAttributeMeta<com.wordpong.api.model.Answer, com.google.appengine.api.datastore.Key>(this, "__key__", "key", com.google.appengine.api.datastore.Key.class);

    /** */
    public final org.slim3.datastore.StringUnindexedAttributeMeta<com.wordpong.api.model.Answer> localeString = new org.slim3.datastore.StringUnindexedAttributeMeta<com.wordpong.api.model.Answer>(this, "localeString", "localeString");

    /** */
    public final org.slim3.datastore.StringUnindexedAttributeMeta<com.wordpong.api.model.Answer> questionDescription = new org.slim3.datastore.StringUnindexedAttributeMeta<com.wordpong.api.model.Answer>(this, "questionDescription", "questionDescription");

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.wordpong.api.model.Answer, com.google.appengine.api.datastore.Key> questionKey = new org.slim3.datastore.CoreAttributeMeta<com.wordpong.api.model.Answer, com.google.appengine.api.datastore.Key>(this, "questionKey", "questionKey", com.google.appengine.api.datastore.Key.class);

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.wordpong.api.model.Answer, com.google.appengine.api.datastore.Key> userKey = new org.slim3.datastore.CoreAttributeMeta<com.wordpong.api.model.Answer, com.google.appengine.api.datastore.Key>(this, "userKey", "userKey", com.google.appengine.api.datastore.Key.class);

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.wordpong.api.model.Answer, java.lang.Long> version = new org.slim3.datastore.CoreAttributeMeta<com.wordpong.api.model.Answer, java.lang.Long>(this, "version", "version", java.lang.Long.class);

    private static final AnswerMeta slim3_singleton = new AnswerMeta();

    /**
     * @return the singleton
     */
    public static AnswerMeta get() {
       return slim3_singleton;
    }

    /** */
    public AnswerMeta() {
        super("Answer", com.wordpong.api.model.Answer.class);
    }

    @Override
    public com.wordpong.api.model.Answer entityToModel(com.google.appengine.api.datastore.Entity entity) {
        com.wordpong.api.model.Answer model = new com.wordpong.api.model.Answer();
        model.setAnswers(toList(java.lang.String.class, entity.getProperty("answers")));
        model.setKey(entity.getKey());
        model.setLocaleString((java.lang.String) entity.getProperty("localeString"));
        model.setQuestionDescription((java.lang.String) entity.getProperty("questionDescription"));
        model.setQuestionKey((com.google.appengine.api.datastore.Key) entity.getProperty("questionKey"));
        model.setUserKey((com.google.appengine.api.datastore.Key) entity.getProperty("userKey"));
        model.setVersion((java.lang.Long) entity.getProperty("version"));
        return model;
    }

    @Override
    public com.google.appengine.api.datastore.Entity modelToEntity(java.lang.Object model) {
        com.wordpong.api.model.Answer m = (com.wordpong.api.model.Answer) model;
        com.google.appengine.api.datastore.Entity entity = null;
        if (m.getKey() != null) {
            entity = new com.google.appengine.api.datastore.Entity(m.getKey());
        } else {
            entity = new com.google.appengine.api.datastore.Entity(kind);
        }
        entity.setProperty("answers", m.getAnswers());
        entity.setUnindexedProperty("localeString", m.getLocaleString());
        entity.setUnindexedProperty("questionDescription", m.getQuestionDescription());
        entity.setProperty("questionKey", m.getQuestionKey());
        entity.setProperty("userKey", m.getUserKey());
        entity.setProperty("version", m.getVersion());
        entity.setProperty("slim3.schemaVersion", 1);
        return entity;
    }

    @Override
    protected com.google.appengine.api.datastore.Key getKey(Object model) {
        com.wordpong.api.model.Answer m = (com.wordpong.api.model.Answer) model;
        return m.getKey();
    }

    @Override
    protected void setKey(Object model, com.google.appengine.api.datastore.Key key) {
        validateKey(key);
        com.wordpong.api.model.Answer m = (com.wordpong.api.model.Answer) model;
        m.setKey(key);
    }

    @Override
    protected long getVersion(Object model) {
        com.wordpong.api.model.Answer m = (com.wordpong.api.model.Answer) model;
        return m.getVersion() != null ? m.getVersion().longValue() : 0L;
    }

    @Override
    protected void assignKeyToModelRefIfNecessary(com.google.appengine.api.datastore.AsyncDatastoreService ds, java.lang.Object model) {
    }

    @Override
    protected void incrementVersion(Object model) {
        com.wordpong.api.model.Answer m = (com.wordpong.api.model.Answer) model;
        long version = m.getVersion() != null ? m.getVersion().longValue() : 0L;
        m.setVersion(Long.valueOf(version + 1L));
    }

    @Override
    protected void prePut(Object model) {
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
        com.wordpong.api.model.Answer m = (com.wordpong.api.model.Answer) model;
        writer.beginObject();
        org.slim3.datastore.json.JsonCoder encoder = null;
        if(m.getAnswers() != null){
            writer.setNextPropertyName("answers");
            encoder = new org.slim3.datastore.json.Default();
            writer.beginArray();
            for(java.lang.String v : m.getAnswers()){
                encoder.encode(writer, v);
            }
            writer.endArray();
        }
        if(m.getKey() != null){
            writer.setNextPropertyName("key");
            encoder = new org.slim3.datastore.json.Default();
            encoder.encode(writer, m.getKey());
        }
        if(m.getLocaleString() != null){
            writer.setNextPropertyName("localeString");
            encoder = new org.slim3.datastore.json.Default();
            encoder.encode(writer, m.getLocaleString());
        }
        if(m.getQuestionDescription() != null){
            writer.setNextPropertyName("questionDescription");
            encoder = new org.slim3.datastore.json.Default();
            encoder.encode(writer, m.getQuestionDescription());
        }
        if(m.getQuestionKey() != null){
            writer.setNextPropertyName("questionKey");
            encoder = new org.slim3.datastore.json.Default();
            encoder.encode(writer, m.getQuestionKey());
        }
        if(m.getUserKey() != null){
            writer.setNextPropertyName("userKey");
            encoder = new org.slim3.datastore.json.Default();
            encoder.encode(writer, m.getUserKey());
        }
        if(m.getVersion() != null){
            writer.setNextPropertyName("version");
            encoder = new org.slim3.datastore.json.Default();
            encoder.encode(writer, m.getVersion());
        }
        writer.endObject();
    }

    @Override
    protected com.wordpong.api.model.Answer jsonToModel(org.slim3.datastore.json.JsonRootReader rootReader, int maxDepth, int currentDepth) {
        com.wordpong.api.model.Answer m = new com.wordpong.api.model.Answer();
        org.slim3.datastore.json.JsonReader reader = null;
        org.slim3.datastore.json.JsonCoder decoder = null;
        reader = rootReader.newObjectReader("answers");
        decoder = new org.slim3.datastore.json.Default();
        {
            java.util.ArrayList<java.lang.String> elements = new java.util.ArrayList<java.lang.String>();
            org.slim3.datastore.json.JsonArrayReader r = rootReader.newArrayReader("answers");
            if(r != null){
                reader = r;
                int n = r.length();
                for(int i = 0; i < n; i++){
                    r.setIndex(i);
                    java.lang.String v = decoder.decode(reader, (java.lang.String)null)                    ;
                    if(v != null){
                        elements.add(v);
                    }
                }
                m.setAnswers(elements);
            }
        }
        reader = rootReader.newObjectReader("key");
        decoder = new org.slim3.datastore.json.Default();
        m.setKey(decoder.decode(reader, m.getKey()));
        reader = rootReader.newObjectReader("localeString");
        decoder = new org.slim3.datastore.json.Default();
        m.setLocaleString(decoder.decode(reader, m.getLocaleString()));
        reader = rootReader.newObjectReader("questionDescription");
        decoder = new org.slim3.datastore.json.Default();
        m.setQuestionDescription(decoder.decode(reader, m.getQuestionDescription()));
        reader = rootReader.newObjectReader("questionKey");
        decoder = new org.slim3.datastore.json.Default();
        m.setQuestionKey(decoder.decode(reader, m.getQuestionKey()));
        reader = rootReader.newObjectReader("userKey");
        decoder = new org.slim3.datastore.json.Default();
        m.setUserKey(decoder.decode(reader, m.getUserKey()));
        reader = rootReader.newObjectReader("version");
        decoder = new org.slim3.datastore.json.Default();
        m.setVersion(decoder.decode(reader, m.getVersion()));
        return m;
    }
}